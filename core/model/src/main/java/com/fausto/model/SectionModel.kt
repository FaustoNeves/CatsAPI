package com.fausto.model

class SectionModel(
    val section: String = "", var breedsList: List<BreedsModel>
) {

    fun buildAllSections(breedQuery: String? = null): List<SectionModel> {
        var filteredBreedsList = emptyList<BreedsModel>()
        /**
         * Se breedQuery (texto da caixa de pesquisa) não for nulo,
         * filtra breedsList (resultado da requisição) através do primeiro caractere.
         * replaceFirstChar é utilizado para transformar o primeiro caractere em caixa alta
         * pois os nomes das raças (breedModel.name) se iniciam em caixa alta.
         * */

        if (breedQuery?.isNotEmpty() == true) {
                filteredBreedsList = breedsList.filter { breedModel ->
                    breedModel.name.startsWith(breedQuery.replaceFirstChar { firstChar -> firstChar.uppercase() })
                }
        }

        val breedsListToBeGrouped: List<BreedsModel> =
            /**
             * Resultado inicial onde nenhuma busca foi realizada
             * */
            if (filteredBreedsList.isEmpty() && breedQuery.isNullOrBlank()) {
                breedsList
            } else {
                /**
                 * Filtra breedsList baseado no texto de pesquisa (breedQuery)
                 * */
                breedsList.filter { breedModel ->
                    breedQuery?.replaceFirstChar { firstChar -> firstChar.uppercase() }
                        ?.let { breedModel.name.startsWith(it) } == true
                }
            }

        /**
         * groupBy retorna um mapa cujo valores é do tipo Lista<T>
         * mapValues retorna um novo mapa mantendo as chaves e aplicando o Transform à todos os values
         * */
        val groupedBySection =
            breedsListToBeGrouped.groupBy { breedQuery?.first()?.uppercase() ?: it.section }
                .mapValues { it.value.toList() }

        /**
         * Retorna uma lista de SectionModel
         * */
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