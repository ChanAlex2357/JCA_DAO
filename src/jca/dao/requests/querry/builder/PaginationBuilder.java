package jca.dao.requests.querry.builder;

class PaginationBuilder {
    static public String getPagination(String database,int offset,int nbPagination){
        String limit = "";
        if (nbPagination > 0 ) {
            limit = " LIMIT "+nbPagination;
        }
        if (offset < 0) {
            return limit;
        }
        /// Pagination pour postgresql
        if (database.equals("postgresql")) {
            limit += " OFFSET "+offset;
        }
        return limit;
    }
}
