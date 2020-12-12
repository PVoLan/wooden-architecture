package ru.pvolan.uitools.async_task;

import android.os.Handler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ru.pvolan.tools.ex_res.ExceptionalResult;

//AsyncTask is deprecated since API 30. So we'll create our own, even better.
public abstract class AsyncTaskEx<TParam, TResult> {

    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public void execute(final TParam param){

        Handler handler = new Handler();

        onPreExecute();

        threadPoolExecutor.execute(() -> {

            ExceptionalResult<TResult> exceptionalResult;

            try {
                TResult res = doInBackground(param);
                exceptionalResult = ExceptionalResult.createOk(res);
            } catch (Exception e) {
                exceptionalResult = ExceptionalResult.createError(e);
            }

            ExceptionalResult<TResult> finalExceptionalResult = exceptionalResult;

            handler.post(() -> onPostExecute(finalExceptionalResult));
        });
    }

    protected abstract void onPreExecute();
    protected abstract TResult doInBackground(TParam param) throws Exception;
    protected abstract void onPostExecute(ExceptionalResult<TResult> result);

}
