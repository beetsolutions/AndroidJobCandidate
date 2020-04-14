package app.storytel.candidate.com

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers(
        val main: CoroutineDispatcher,
        val io: CoroutineDispatcher
)