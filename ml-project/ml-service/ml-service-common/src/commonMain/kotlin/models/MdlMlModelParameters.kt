package api.kotlinproject.common.models

data class MdlMlModelParameters(
/* learning_rate */
var learningRate: kotlin.Double = 0.0,

/* max_depth */
var maxDepth: kotlin.Int = 0,

/* sub_sample */
var subSample: kotlin.Double = 0.0,

/* gamma */
var gamma: kotlin.Int = 0,

/* num_round */
var numRound: kotlin.Int = 0,

/* tree_method */
var treeMethod: kotlin.String = "1",

/* refresh_leaf */
var refreshLeaf: kotlin.Int = 1,

/* process_type */
var processType: kotlin.String = "default",

/* updater */
var updater: kotlin.String = "1"
){
    fun isEmpty() = this == NONE

    fun deepCopy(): MdlMlModelParameters = copy(
    )

    companion object {
        private val NONE = MdlMlModelParameters()
    }

}