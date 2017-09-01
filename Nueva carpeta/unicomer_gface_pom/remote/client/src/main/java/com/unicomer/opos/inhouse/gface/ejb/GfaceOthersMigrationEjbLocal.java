package com.unicomer.opos.inhouse.gface.ejb;

import javax.ejb.Remote;

@Remote
public interface GfaceOthersMigrationEjbLocal {

    boolean migrationToGface();

}
