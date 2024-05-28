package jca.dao.requests;

public class PaginationBuilder {
    static public String getPagination(String database,int offset,int nbPagination){
        String limit = "";
        if (offset < 0) {
            return limit;
        }
        /// Pagination pour postgresql
        if (database.equals("postgresql")) {
            limit = " LIMIT "+nbPagination+" OFFSET "+offset;
        }
        return limit;
    }
}
