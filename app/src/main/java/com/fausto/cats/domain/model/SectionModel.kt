package com.fausto.cats.domain.model

internal class SectionModel(
    val section: String? = null, val breedsList: List<BreedsModel>
) {
    fun buildSections(breedsList: List<BreedsModel>): List<SectionModel> {
        val groupedBySection = breedsList.groupBy { it.section }.mapValues { it.value.toList() }
        return groupedBySection.map { (section, breeds) ->
            SectionModel(section, breeds)
        }
    }
}