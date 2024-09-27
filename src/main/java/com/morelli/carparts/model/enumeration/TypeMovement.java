package com.morelli.carparts.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeMovement {

    ADD_PRODUCT,
    UPDATE_PRODUCT,
    DELETE_PRODUCT,
    ADD_CATEGORY,
    UPDATE_CATEGORY,
    DELETE_CATEGORY,
    ADD_VARIANT,
    UPDATE_VARIANT,
    DELETE_VARIANT;
}
