package xyz.thetbw.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.constant.SettingKey;
import xyz.thetbw.blog.data.dao.UserDao;
import xyz.thetbw.blog.data.dao.UserSettingDao;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.data.entity.UserSetting;
import xyz.thetbw.blog.exception.*;
import xyz.thetbw.blog.util.AESEncodeUtils;
import xyz.thetbw.blog.util.EncodeUtils;
import xyz.thetbw.blog.util.RandomUtils;
import xyz.thetbw.blog.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

/**
 * 用户相关
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    HttpServletRequest request;


    @Value("${aes.password}")
    String aesPassword;

    @Autowired
    UserDao userDao;

    @Autowired
    UserSettingDao userSettingDao;

    private String defaultAvatar="//secure.gravatar.com/avatar/d41d8cd98f00b204e9800998ecf8427e?s=80&r=G&d=mm";

    /**
     * 检查用户是否登陆
     */
    public void findUser() throws UserException {
        if (request.getSession().getAttribute(FieldKey.USER_ACCESS)!=null)
            return;
        getUserFromCookie();


    }


    public void checkUser(String user_name,String user_pass) throws UserNotFountException,UserAccessErrorException {
        User user = userDao.getUserByName(user_name);
        if (user==null)
            throw new UserNotFountException();
        if (user.getUser_pass()!=user_pass.hashCode())
            throw new UserAccessErrorException();
        request.getSession().setAttribute(FieldKey.USER_ACCESS,user);
    }

    /**
     * 获取当前登陆用户信息
     * @return
     */
    public User getUser(){
        try {
            User user = (User) request.getSession().getAttribute(FieldKey.USER_ACCESS);
            if (user!=null)
                user.clearPass();
            return user;
        }catch (Exception e){
            LOG.warn("获取用户失败"+e.getClass().getName()+":"+e.getMessage());
        }
        return null;
    }

    public User getUser(int user_id){
        User user = userDao.get(user_id);
        if (user!=null)
            user.clearPass();
        return user;
    }

    public User getAdminUser(){
        User user = userDao.getAdminUser();
        if (user!=null)
            user.clearPass();
        return user;
    }

    @Transactional
    public User creatMemberUser(String user_name,String user_nickname,String user_pass,String user_email) throws StringNotExistException, StringIsNoneException, UserAlreadyExitsException {
        User user = createUser(
                user_name,
                user_nickname,
                user_pass,
                user_email,
                defaultAvatar,
                User.USER_ROLE_MENMBER
        );
        return user;
    }

    /**
     * 创建游客账户
     *
     * @param nick_name 昵称
     * @param email 邮箱
     * @return
     * @throws UserCreateException
     * @throws UserAlreadyExitsException
     * @throws StringNotExistException
     * @throws StringIsNoneException
     */
    @Transactional
    public User createGuestUser(String nick_name,String email) throws  StringNotExistException, StringIsNoneException {
        User user=null;
        int i=0;
        do {
            try {
                user = createUser(
                        RandomUtils.getInstance().RandomStringBuilder(10),
                        "游客"+RandomUtils.getInstance().RandomStringBuilder(5),
                        RandomUtils.getInstance().RandomStringBuilder(10),
                        StringUtils.getInstance().validString(email),
                        defaultAvatar,
                        User.USER_ROLE_GUEST);
            }catch (UserAlreadyExitsException e){ }
            if (i>11){
                LOG.warn("创建游客用户失败，未能找到不重复的用户名,请检查数据库:"+user);
                throw new RuntimeException("创建游客用户失败，未能找到不重复的用户名");
            }
            i++;
        }while (user==null||user.getUser_id()==0);
        return user;
    }


    /**
     * 创建用户
     *
     * @param user_name 用户名
     * @param user_nickname 昵称
     * @param pass 密码
     * @param email 邮箱
     * @param avatar 头像
     * @param role 用户类型
     * @return
     * @throws StringNotExistException
     * @throws StringIsNoneException
     */
    private User createUser(String user_name,String user_nickname,String pass,String email,String avatar,int role) throws StringNotExistException, StringIsNoneException, UserAlreadyExitsException {
        StringUtils utils = StringUtils.getInstance();
        String name=utils.validString(user_name);
        String nickname = utils.htmlTrans(utils.validString(user_nickname));
        String password = StringUtils.getInstance().validString(pass);
        User user=null;
        user = userDao.getUserByNickName(nickname);
        if (user==null)
            user=userDao.getUserByName(name);
        if (user!=null) {
            LOG.warn("创建用户失败，用户已经存在："+user);
            throw new UserAlreadyExitsException("用户已经存在");
        }
        user = new User();
        user.setUser_name(name);
        user.setUser_nickname(nickname);
        user.setUser_pass(password.hashCode());
        user.setUser_avatar_url(avatar);
        user.setUser_email(email);
        user.setUser_role(role);
        userDao.add(user);

        if (user.getUser_id()==0){
            LOG.warn("用户创建失败："+user.toString());
            return null;
        }
        return user;
    }


    /**
     * 从cookie加载用户 然后放进session里，如果有的话
     */
    private void getUserFromCookie() throws UserException {
        Cookie[] cookies = request.getCookies();
        if (cookies==null)
            return;
        for (Cookie cookie:cookies){
            if (FieldKey.USER_ACCESS.equals(cookie.getName())){
                getUserFromDatabase(cookie.getValue());
                return;
            }
        }
    }

    public void deleteAccessFromCookie(HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(FieldKey.USER_ACCESS)) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

    /**
     * 更新用户信息 (仅管理员)
     * @param hashMap
     */
    @Transactional
    public void updateUserInfo(HashMap<String,String> hashMap){
        String user_nickname = hashMap.get(SettingKey.USER_NICK_NAME);
        String user_email = hashMap.get(SettingKey.USER_EMAIL);
        String user_avatar_url = hashMap.get(SettingKey.USER_AVATAR_URL);
        String commentWithEmail = hashMap.get(SettingKey.COMMENT_WITH_EMAIL);
//        User user =(User) request.getSession().getAttribute(FieldKey.USER_ACCESS);
        User user = userDao.getAdminUser();
        user.setUser_email(user_email);
        user.setUser_nickname(user_nickname);
        user.setUser_avatar_url(user_avatar_url);
        userDao.update(user);
        UserSetting userSetting = userSettingDao.getWithUserAndKey(user.getUser_id(),SettingKey.COMMENT_WITH_EMAIL);
        if (userSetting==null){
            userSetting = new UserSetting();
            userSetting.setUser_id(user.getUser_id());
            userSetting.setUser_setting_key(SettingKey.COMMENT_WITH_EMAIL);
            userSetting.setUser_setting_value(commentWithEmail);
            userSettingDao.add(userSetting);
        }else {
            userSetting.setUser_setting_value(commentWithEmail);
            userSettingDao.update(userSetting);
        }


    }

    /**
     * 获取用户信息 (仅管理员)
     * @return
     */
    public HashMap<String,String> getUserInfo(){
        HashMap<String,String> hashMap = new HashMap<>();
        User user = userDao.getAdminUser();
        user.clearPass();
        hashMap.put(SettingKey.USER_AVATAR_URL,user.getUser_avatar_url());
        hashMap.put(SettingKey.USER_EMAIL,user.getUser_email());
        hashMap.put(SettingKey.USER_NICK_NAME,user.getUser_nickname());
        List<UserSetting> userSettings = userSettingDao.getAllWithUser(user.getUser_id());
        userSettings.forEach(u->{
            hashMap.put(u.getUser_setting_key(),u.getUser_setting_value());
        });
        return hashMap;
    }

    /**
     * 更新密码，仅限管理员
     * @param oldPass
     * @param newPass
     */
    public void updateUserPass(String oldPass,String newPass,HttpServletResponse response){
        User user = userDao.getAdminUser();
        if (user.getUser_pass()!=oldPass.hashCode())
            throw new RuntimeException("密码错误");
        user.setUser_pass(newPass.hashCode());
        userDao.update(user);
        addUserToCookie(user,response);
    }

    /**
     * 从数据库读取用户
     * @param access 加密的访问密钥
     */
    private void getUserFromDatabase(String access) throws UserException {
        String[] user_access = getUserAccess(access);
        if (user_access==null)
            throw new UserAccessErrorException();
        int user_id =Integer.parseInt(user_access[0]);
        int user_pass = Integer.parseInt(user_access[1]);
        User user = userDao.get(user_id);
        if (user==null)
            throw new UserNotFountException("用户不存在 id:"+user_id+" pass:"+user_pass);
        if (user.getUser_pass()!=user_pass) {
            LOG.warn("用户密码错误 错误的id:"+user_id+" 错误的密码："+user_pass);
            LOG.warn("用户密码错误 user:"+user);
            throw new UserAccessErrorException();
        }
        request.getSession().setAttribute(FieldKey.USER_ACCESS,user);
    }

    /**
     * 把用户id和密码加密
     *
     * @param user_id
     * @param user_password
     * @return
     */
    private String encodeUserAccess(int user_id,int user_password){
        StringBuilder builder = new StringBuilder();
        builder.append(user_id).append("/").append(user_password).append("/")
                .append(RandomUtils.getInstance().RandomStringBuilder(10))
                .append("/");
        LOG.debug("加密前数据："+builder.toString());
        byte[]  result=null;
        try {
             result=  AESEncodeUtils.getInstance().encrypt(builder.toString(),aesPassword);

        } catch (Exception e){
            LOG.error("user_access 加密失败:"+e.getMessage());
        }
        return EncodeUtils.getOurInstance().parseByte2HexStr(result);
    }

    /**
     * 用加密的字符串用获取用户的id和密码
     * @param access
     * @return 字符串数组 索引0 ,1分别是id和密码
     */
    private String[] getUserAccess(String access){
        try {
            EncodeUtils encodeUtils = EncodeUtils.getOurInstance();
            byte[] b =encodeUtils.parseHexStr2Byte(access);
            String s = new String(AESEncodeUtils.getInstance().decrypt(b,aesPassword));
            LOG.debug("解密后数据："+s);
            return s.split("/");
        }catch (Exception e){
            LOG.error("解密密钥失败 错误的密钥："+aesPassword+" 解密的内容："+access);
            return null;
        }

    }

    /**
     * 把用户添加到cookie中
     * @param response
     */
    public void addUserToCookie(User user,HttpServletResponse response){
        String user_access = encodeUserAccess(user.getUser_id(),user.getUser_pass());
        Cookie cookie = new Cookie(FieldKey.USER_ACCESS,user_access);
        cookie.setPath("/");
        cookie.setMaxAge(ConstValue.MAX_COOKIE_AGE);
        response.addCookie(cookie);
    }

}
