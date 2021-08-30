package vic.sample.chatuisample.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope

open class BasicViewModel() : ViewModel() {

    /*
    * To Manage Rxjava Life Cycle
    * */
    val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mDisposables.dispose()
        super.onCleared()
    }

    abstract inner class BaseObserver<T> : Observer<T> {
        private lateinit var disposable : Disposable
        protected abstract fun onSubscribeBase(d: Disposable?)
        protected abstract fun onNextBase(o: T)
        protected abstract fun onErrorBase(e: Throwable?)
        protected abstract fun onCompleteBase()
        override fun onSubscribe(d: Disposable) {
            onSubscribeBase(d)
            disposable = d
            mDisposables.add(disposable)
        }

        override fun onNext(o: T) {
            onNextBase(o)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            onErrorBase(e)
            if (!disposable.isDisposed) {
                mDisposables.remove(disposable)
                disposable.dispose()
            }
        }

        override fun onComplete() {
            onCompleteBase()
            if (!disposable.isDisposed) {
                mDisposables.remove(disposable)
                disposable.dispose()
            }
        }
    }

    /**
     * Configure CoroutineScope injection for production and testing.
     *
     * @receiver ViewModel provides viewModelScope for production
     * @param coroutineScope null for production, injects TestCoroutineScope for unit tests
     * @return CoroutineScope to launch coroutines on
     */
    fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) =
        coroutineScope ?: this.viewModelScope

}