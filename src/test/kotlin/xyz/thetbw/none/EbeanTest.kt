package xyz.thetbw.none

import io.ebean.annotation.Platform
import io.ebean.dbmigration.DbMigration
import org.junit.Test

class EbeanTest {

    @Test
    fun genSql(){
        val dbMigration = DbMigration.create()
        dbMigration.setPlatform(Platform.H2)

        dbMigration.generateMigration()
    }
}