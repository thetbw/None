package xyz.thetbw.none.common.data

import io.ebean.PagedList

/**
 * 请求结果返回
 *
 */
class ReqResult<T>(

    /** 请求操作是否成功 */
    var success:Boolean,

    /** 响应码 */
    var code:Int,

    /** 响应信息 */
    var msg:String

    ) {

    constructor(success: Boolean,code: Int,msg: String,body: T) : this(success,code,msg) {
        this.body = body;
    }

    /**
     * 响应体
     */
    var body: T? = null

    /**
     * 分页信息
     */
    var page: PageInfo? = null

    companion object {
        private const val SUCCESS_CODE: Int = 100;

        /** 创建一个请求成功的返回 */
        fun createSuccessResult() = ReqResult<Unit>(true,SUCCESS_CODE,"请求成功")

        /** 创建一个请求成功的返回，带有返回体 */
        fun <T> createSuccessResult(body: T) = ReqResult(true,SUCCESS_CODE,"请求成功",body)

        /**
         * 创建一个分页的相应
         */
        fun <E,T: PagedList<E>> createPagedResult(body: T): ReqResult<List<E>> {
            val pageInfo  = PageInfo(body.pageIndex,body.pageSize,body.totalPageCount,body.totalCount);
            val pageResult =ReqResult(true, SUCCESS_CODE,"请求成功",body.list)
            pageResult.page = pageInfo
            return pageResult
        }


    }


    /** 分页信息 */
    class PageInfo(
        /** 当前页 */
        val page:Int,
        /** 当前页大小 */
        val pageSize:Int,
        /** 总页数 */
        val totalPage:Int,
        /** 总行数 */
        val totalSize:Int
    )

}