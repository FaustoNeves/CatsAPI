package com.fausto.model

class SectionModel(
    val section: String? = null, val breedsList: List<BreedsModel>
) {
    fun buildSections(breedsList: List<BreedsModel>): List<SectionModel> {
        val groupedBySection = breedsList.groupBy { it.section }.mapValues { it.value.toList() }
        return groupedBySection.map { (section, breeds) ->
            SectionModel(section, breeds)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SectionModel) return false
        if (section != other.section) return false
        if (breedsList != other.breedsList) return false
        return true
    }

    override fun hashCode(): Int {
        var result = section.hashCode()
        result = 31 * result + breedsList.hashCode()
        return result
    }
}