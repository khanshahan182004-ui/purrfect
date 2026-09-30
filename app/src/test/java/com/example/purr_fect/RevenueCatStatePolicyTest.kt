package com.example.purr_fect

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RevenueCatStatePolicyTest {

    @Test
    fun customerInfo_active_entitlement_sets_premium_true() {
        assertTrue(
            RevenueCatStatePolicy.fromCustomerInfo(true)
        )
    }

    @Test
    fun customerInfo_inactive_entitlement_sets_premium_false() {
        assertFalse(
            RevenueCatStatePolicy.fromCustomerInfo(false)
        )
    }

    @Test
    fun restore_active_keeps_premium_active() {
        assertTrue(
            RevenueCatStatePolicy.afterRestore(
                currentPremiumActive = true,
                restoredEntitlementActive = true
            )
        )
    }

    @Test
    fun restore_false_does_not_reset_existing_premium() {
        assertTrue(
            RevenueCatStatePolicy.afterRestore(
                currentPremiumActive = true,
                restoredEntitlementActive = false
            )
        )
    }

    @Test
    fun restore_false_for_non_premium_stays_false() {
        assertFalse(
            RevenueCatStatePolicy.afterRestore(
                currentPremiumActive = false,
                restoredEntitlementActive = false
            )
        )
    }

    @Test
    fun restore_true_activates_non_premium_user() {
        assertTrue(
            RevenueCatStatePolicy.afterRestore(
                currentPremiumActive = false,
                restoredEntitlementActive = true
            )
        )
    }

    @Test
    fun successful_purchase_activates_premium() {
        assertTrue(
            RevenueCatStatePolicy.afterPurchase(true)
        )
    }

    @Test
    fun failed_purchase_does_not_remove_existing_premium() {
        assertTrue(
            RevenueCatStatePolicy.afterPurchaseError(true)
        )
    }

    @Test
    fun failed_purchase_for_non_premium_stays_non_premium() {
        assertFalse(
            RevenueCatStatePolicy.afterPurchaseError(false)
        )
    }

    @Test
    fun failed_restore_does_not_remove_existing_premium() {
        assertTrue(
            RevenueCatStatePolicy.afterRestoreError(true)
        )
    }

    @Test
    fun failed_restore_for_non_premium_stays_non_premium() {
        assertFalse(
            RevenueCatStatePolicy.afterRestoreError(false)
        )
    }
}
