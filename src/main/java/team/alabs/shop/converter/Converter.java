package team.alabs.shop.converter;

public interface Converter<E, T> {
    T convertToDto(E e);

    E convertToEntity(T t);

}
