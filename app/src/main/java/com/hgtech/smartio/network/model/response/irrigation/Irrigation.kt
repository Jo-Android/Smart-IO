package com.hgtech.smartio.network.model.response.irrigation

import com.hgtech.smartio.network.model.response.assign.state.State

data class Irrigation(
    val state: State,
    val irrigation: List<IrrigationInfo>
)
