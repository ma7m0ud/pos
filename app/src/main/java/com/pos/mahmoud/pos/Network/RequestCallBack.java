package com.pos.mahmoud.pos.Network;

import com.pos.mahmoud.pos.Helpers.TransactionModel;

public interface RequestCallBack {


   void onSuccess(TransactionModel model);
   void onError();
}