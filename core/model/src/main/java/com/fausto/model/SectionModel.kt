package com.fausto.model

class SectionModel(
    val section: String? = null, val breedsList: List<BreedsModel>
) {
    fun buildSections(breedQuery: String? = null): List<SectionModel> {
        var filteredBreedsList = emptyList<BreedsModel>()
        if (breedQuery?.isNotEmpty() == true) {
                filteredBreedsList = breedsList.filter { breedModel ->
                    breedModel.name.startsWith(breedQuery.replaceFirstChar { firstCharacter ->
                        firstCharacter.uppercase()
                    })
                }
        }

        val breedsListToBeGrouped: List<BreedsModel> = filteredBreedsList.ifEmpty { breedsList }

        val groupedBySection =
            breedsListToBeGrouped.groupBy { breedQuery?.first()?.uppercase() ?: it.section }
                .mapValues { it.value.toList() }

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