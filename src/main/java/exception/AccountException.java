package exception;

import entity.Account;

public class AccountException extends Exception{
    public AccountException(){
        super();
    }
    public AccountException(String message){
        super(message);
    }

}
