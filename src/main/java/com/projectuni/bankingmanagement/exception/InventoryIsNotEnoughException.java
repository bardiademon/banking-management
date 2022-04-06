package com.projectuni.bankingmanagement.exception;

public final class InventoryIsNotEnoughException extends Exception
{
    public InventoryIsNotEnoughException()
    {
        super("Inventory is not enough");
    }
}
