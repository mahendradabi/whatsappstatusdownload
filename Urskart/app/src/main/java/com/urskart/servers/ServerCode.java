package com.urskart.servers;


/**
 * Created by xyz on 22-02-2018.
 */

public interface ServerCode {
   //constant for server KONWN response code
   int REQUEST_OK=200;
   int REQEST_NO_CONTENT=204;
   int REQUEST_PAGE_NOT_FOUND=404;
   int REQUEST_BAD=400;
   int REQUEST_FORBIDDEN=403;
   int REQUEST_INTERNAL_SERVER_ERROR=500;



   //constant server request code
   int LOGIN_CODE=1;
   int REGISTER_CODE=2;
   int GET_CATEGORY=3;
   int GET_PRODUCTS=4;
   int ADD_FAVORITE=5;
   int ADD_CART=6;






}
