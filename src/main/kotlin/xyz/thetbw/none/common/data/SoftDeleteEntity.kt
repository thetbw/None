package xyz.thetbw.none.common.data

import io.ebean.annotation.SoftDelete
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class SoftDeleteEntity:BaseEntity() {

    @SoftDelete
    var  deleted:Boolean = false;

}