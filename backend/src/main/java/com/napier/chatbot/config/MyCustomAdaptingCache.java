/*package com.napier.chatbot.config;

import java.util.concurrent.Callable;

import javax.cache.Cache;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

import org.springframework.cache.support.AbstractValueAdaptingCache;

public class MyCustomAdaptingCache extends AbstractValueAdaptingCache {

    private final Cache<String, Object> cache;

    public MyCustomAdaptingCache(javax.cache.Cache<String, Object> jcache) {
        this(jcache, true);
    }

    public MyCustomAdaptingCache(Cache<String, Object> jcache, boolean allowNullValues) {
        super(allowNullValues);        
        this.cache = jcache;
    }

    public final String getName() {
        return this.cache.getName();
    }

  
    public final javax.cache.Cache<String, Object> getNativeCache() {
        return this.cache;
    }

    
    protected Object lookup(Object key) {
        return this.cache.get((String)key);
    }

   
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            return this.cache.invoke((String)key, new ValueLoaderEntryProcessor<T>(), valueLoader);
        }
        catch (EntryProcessorException ex) {
            throw new ValueRetrievalException(key, valueLoader, ex.getCause());
        }
    }

   
    public void put(Object key, Object value) {
        this.cache.put((String)key, toStoreValue(value));
    }

  
    public ValueWrapper putIfAbsent(Object key, Object value) {
        boolean set = this.cache.putIfAbsent((String)key, toStoreValue(value));
        return (set ? null : get(key));
    }

  
    public void evict(Object key) {
        this.cache.remove((String)key);
    }

 
    public void clear() {
        this.cache.removeAll();
    }

    private class ValueLoaderEntryProcessor<T> implements EntryProcessor<String, Object, T> {

        @SuppressWarnings("unchecked")
     
        public T process(MutableEntry<String, Object> entry, Object... arguments)
                throws EntryProcessorException {
            Callable<T> valueLoader = (Callable<T>) arguments[0];
            if (entry.exists()) {
                return (T) fromStoreValue(entry.getValue());
            }
            else {
                T value;
                try {
                    value = valueLoader.call();
                }
                catch (Exception ex) {
                    throw new EntryProcessorException("Value loader '" + valueLoader + "' failed " +
                            "to compute  value for key '" + entry.getKey() + "'", ex);
                }
                entry.setValue(toStoreValue(value));
                return value;
            }
        }
    }

}*/