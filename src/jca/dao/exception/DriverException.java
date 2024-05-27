package jca.dao.exception;

public class DriverException extends Exception{
    public DriverException(String driver , ClassNotFoundException driverErrors){
        super(  
            "Le driver de votre sgbd : "+driver+" est introuvable",
            driverErrors.getCause()
        );
    }
}
