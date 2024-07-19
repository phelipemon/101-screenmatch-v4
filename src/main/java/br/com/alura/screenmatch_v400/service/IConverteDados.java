package br.com.alura.screenmatch_v400.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);
}
