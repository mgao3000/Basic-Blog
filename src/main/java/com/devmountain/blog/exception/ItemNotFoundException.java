package com.devmountain.blog.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String arg0, Throwable arg1,
                                 boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public ItemNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ItemNotFoundException(String arg0) {
        super(arg0);
    }

    public ItemNotFoundException(Throwable arg0) {
        super(arg0);
    }

}
