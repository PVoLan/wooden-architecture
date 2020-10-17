package ru.pvolan.tools.ex_res;

public class ExceptionalResult<TResult> {

    private TResult data;
    private Exception error;

    public static <T> ExceptionalResult<T> createOk(T res){
        return new ExceptionalResult<>(res, null);
    }

    public static <T> ExceptionalResult<T> createError(Exception error){
        return new ExceptionalResult<>(null, error);
    }

    private ExceptionalResult(TResult data, Exception error) {
        this.data = data;
        this.error = error;
    }

    public TResult getData () {
        return data;
    }

    public Exception getError () {
        return error;
    }
}
