package io.github.a2kaido.emvco.cpm.model

enum class CpmTag(val tag: String) {
    PayloadFormatIndicator("85"),
    ApplicationTemplate("61"),
    ApplicationSpecificTransparentTemplate("63"),
    CommonDataTemplate("62"),
    CommonDataTransparentTemplate("64"),
    ApplicationDefinitionFileName("4F"),
    ApplicationLabel("50"),
    Track2EquivalentData("57"),
    ApplicationPAN("5A"),
    CardholderName("5F20"),
    LanguagePreference("5F2D"),
    IssuerURL("5F50"),
    ApplicationVersionNumber("9F08"),
    IssuerApplicationData("9F10"),
    TokenRequestorID("9F19"),
    PaymentAccountReference("9F24"),
    Last4DigitsOfPAN("9F25"),
    ApplicationCryptogram("9F26"),
    ApplicationTransactionCounter("9F36"),
    UnpredictableNumber("9F37")
}
