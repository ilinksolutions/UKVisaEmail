package com.ilinksolutions.UKVisaEmail.exceptions;

public enum ErrorCode
{
	GENERAL_ERROR_CODE (1000),
	DATABASE_ERROR_CODE (2000),
	EMAIL_ERROR_CODE (3000),
	SECURITY_ERROR_CODE (4000);
	
    private final int errorCode;

    ErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public int getLevelCode()
    {
        return this.errorCode;
    }
}
