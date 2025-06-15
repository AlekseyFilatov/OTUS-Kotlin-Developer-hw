package api.kotlinproject.common.models

data class MdlMlModelParameters(
/* learning_rate */
var learningRate: kotlin.Double? = null,

/* max_depth */
var maxDepth: kotlin.Int? = null,

/* sub_sample */
var subSample: kotlin.Double? = null,

/* gamma */
var gamma: kotlin.Int? = null,

/* num_round */
var numRound: kotlin.Int? = null,

/* tree_method */
var treeMethod: kotlin.String? = null,

/* refresh_leaf */
var refreshLeaf: kotlin.Int? = 1,

/* process_type */
var processType: kotlin.String? = "default",

/* updater */
var updater: kotlin.String? = null
)