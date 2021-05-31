package team.alabs.wso3.converter;

public interface Converter<E, T> {
    T convertToDto(E e);

    E convertToEntity(T t);

}
