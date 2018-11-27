package me.shtanko.topgithub.ui.main

import io.reactivex.Observable
import me.shtanko.data.entity.User
import me.shtanko.domain.MainRepository
import me.shtanko.domain.RxUseCase
import me.shtanko.domain.executor.PostExecutionThread
import me.shtanko.domain.executor.ThreadExecutor
import javax.inject.Inject

class MainUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val mainRepository: MainRepository
) : RxUseCase<List<User>, MainUseCase.Params>(threadExecutor, postExecutionThread) {


    override fun buildObservable(params: Params): Observable<List<User>> {
        return mainRepository.getUsers(page = params.page, perPage = params.perPage, since = params.since)
            .toObservable()
    }

    data class Params(
        val page: Int,
        val perPage: Int,
        val since: Int
    )

}