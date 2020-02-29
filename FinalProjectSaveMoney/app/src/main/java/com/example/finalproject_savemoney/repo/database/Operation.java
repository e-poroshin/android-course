package com.example.finalproject_savemoney.repo.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class Operation {

    @Embedded
    private OperationEntity operation;

    @Relation(parentColumn = "operation_category_id", entityColumn = "category_id", entity = CategoryEntity.class)
    private CategoryEntity category;

    @Relation(parentColumn = "operation_account_id", entityColumn = "account_id", entity = AccountEntity.class)
    private AccountEntity account;

    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
