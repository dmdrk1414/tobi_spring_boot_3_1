package springbook.user.learningtest.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
