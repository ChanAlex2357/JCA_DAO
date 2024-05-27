package jca.dao.models.annotations;

public @interface ForeignKey {
    public String entityName();
    public String primaryKeyAttribut();
}
