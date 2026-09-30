package com.example.purr_fect

/**
 * Pure RevenueCat entitlement-state rules.
 *
 * Keeps state decisions independent from Android UI and the RevenueCat SDK,
 * so they can be verified automatically with local unit tests.
 */
object RevenueCatStatePolicy {

    /**
     * A successful CustomerInfo response is authoritative.
     */
    fun fromCustomerInfo(entitlementActive: Boolean): Boolean {
        return entitlementActive
    }

    /**
     * A restore operation must never downgrade an already-active local state
     * merely because the restore response reports no entitlement.
     */
    fun afterRestore(
        currentPremiumActive: Boolean,
        restoredEntitlementActive: Boolean
    ): Boolean {
        return currentPremiumActive || restoredEntitlementActive
    }

    /**
     * Purchase success uses the returned entitlement state.
     */
    fun afterPurchase(entitlementActive: Boolean): Boolean {
        return entitlementActive
    }

    /**
     * Errors/cancellation must not change entitlement state.
     */
    fun afterPurchaseError(currentPremiumActive: Boolean): Boolean {
        return currentPremiumActive
    }

    fun afterRestoreError(currentPremiumActive: Boolean): Boolean {
        return currentPremiumActive
    }
}
