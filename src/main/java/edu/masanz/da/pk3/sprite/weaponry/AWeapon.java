package edu.masanz.da.pk3.sprite.weaponry;

import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.interfaces.IIsDisposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AWeapon {

    protected ASprite owner;
    protected boolean isRechargable;
    protected int ammunition;

    public AWeapon(ASprite owner) {
        this.owner = owner;
        isRechargable = false;
        ammunition = 0;
    }

    public AWeapon(ASprite owner, boolean isRechargable, int ammunition) {
        this.owner = owner;
        this.isRechargable = isRechargable;
        this.ammunition = ammunition;
    }

    public ASprite getOwner() {
        return owner;
    }

    public void setOwner(ASprite owner) {
        this.owner = owner;
    }

    public boolean isRechargable() {
        return isRechargable;
    }

    public void setRechargable(boolean rechargable) {
        isRechargable = rechargable;
    }

    public void recharge(int ammunition) {
        this.ammunition += ammunition;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }


    public abstract List<AShot> shoot();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " owned by " + owner.getClass().getSimpleName()
                + " with " + ammunition + " ammunition" + (isRechargable ? " rechargable" : "")
                + (this instanceof IIsDisposable ? ((IIsDisposable) this).isDisposable() ? " and disposable" : "" : "");
    }

    @Override
    public boolean equals(Object o) {
        return this.getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }

}
