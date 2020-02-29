package xyz.thetbw.blog.util;



import java.util.Random;

/**
 * 随机字符串的生成
 */
public class RandomUtils {
    private static Random random;
    private static RandomUtils ourInstance = new RandomUtils();

    public static RandomUtils getInstance() {
        random = new Random();
        return ourInstance;
    }

    private RandomUtils() {
    }


    public void test(){
        System.out.println(RandomUtils.getInstance().RandomStringBuilder(20));
    }

    /**
     * 随机生成字符串
     * @param StringLength 字符串长度
     * @return
     */
    public String RandomStringBuilder(int StringLength){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<=StringLength;i++){
            int temp =Math.abs(random.nextInt());
            if(temp%3==0){
                builder.append(RandomStringBuilder(0,'0','9'));
            }else if(temp%3==1){
                builder.append(RandomStringBuilder(0,'A','Z'));
            }else if(temp%3==2) {
                builder.append(RandomStringBuilder(0, 'a', 'z'));
            }
        }

        return builder.toString();
    }


    /**
     * 随机生成字符串
     * @param StringLength 字符串长度
     * @param begin 开始字符
     * @param end 结束字符
     * @return 生成的字符串
     */
    private String RandomStringBuilder(int StringLength,char begin,char end){
        StringBuilder builder  = new StringBuilder();
        for(int i = 0;i<=StringLength;i++){
            char c = (char) (random.nextInt(end)%(end-begin+1)+begin);
            builder.append(c);
        }
        return builder.toString();
    }
}
