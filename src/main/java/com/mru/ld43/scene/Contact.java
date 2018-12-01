/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
import java.util.Arrays;

/**
 *
 * @author matt
 */
public class Contact implements EntityComponent{
    private final EntityId[] contactIds;

    public Contact(EntityId[] contactIds) {
        this.contactIds = contactIds;
    }

    public EntityId[] getContactIds() {
        return contactIds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.deepHashCode(this.contactIds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (!Arrays.equals(this.contactIds, other.contactIds)) {
            return false;
        }
        return true;
    }
    
}
