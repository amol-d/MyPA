package com.msc.idol.mypa.model.string;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amdam_000 on 5/27/2017.
 */

public class StringOP extends RealmObject{
    public long id;
    public String msg;

    public StringOP(){}
    public StringOP(long id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
