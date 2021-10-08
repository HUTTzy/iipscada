package iipscada.paginate;

/**
 * 分页查询过滤器
 * 推荐内部类实现
 *
 * @Auther: mao
 * @Date: 18-11-24 23:50
 */
@FunctionalInterface
public interface PaginateFilter {
    /**
     * 过滤/sql语句
     * eg:常用 filter 过滤规则
     * ${val}>100            #只选择大于100的数值
     * ${val}=${filterVal}   #只选择等于过滤的记录
     * ${val}like'Mao%'      #只选择匹配Mao(.)*
     * ${val}like'%Mao%'     #只选择匹配包含Mao的字符串
     *
     * @param attr 字段名称
     * @return filter 过滤规则
     **/
    String doFilter(String attr);
}
