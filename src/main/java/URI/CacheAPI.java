package URI;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;
/**
 * @author Crunchify.com
 */

public class CacheAPI<K, T> {

    private long timeToLive;
    private LRUMap memoryCacheMap;

    public CacheAPI() {
    }

    protected class MemoryCacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected MemoryCacheObject(T value) {
            this.value = value;
        }
    }
    /**
     * Contructor del cache
     * @param timeToLive Tiempo de vida del cache
     * @param timerInterval Intervalo
     * @param maxItems Capacidad del cache
     */
    public CacheAPI(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;

        memoryCacheMap = new LRUMap(maxItems);

        if (this.timeToLive > 0 && timerInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }
    /**
     * Funcion para guardar datos en cache
     * @param K busqueda realizada
     * @param T JSON
     */
    public void put(K key, T value) {
        synchronized (memoryCacheMap) {
            memoryCacheMap.put(key, new MemoryCacheObject(value));
        }
    }
    /**
     * Funcion para obtener datos de cache
     * @param K busqueda realizada
     * @return JSON
     */
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (memoryCacheMap) {
            MemoryCacheObject c = (MemoryCacheObject) memoryCacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }
    /**
     * Funcion para eliminar un dato de cache
     * @param K busqueda realizada
     */
    public void remove(K key) {
        synchronized (memoryCacheMap) {
            memoryCacheMap.remove(key);
        }
    }
    /**
     * Funcion para saber el tamaño del cache
     * @return size
     */
    public int size() {
        synchronized (memoryCacheMap) {
            return memoryCacheMap.size();
        }
    }
    /**
     * Funcion para limpiar el cache
     */
    @SuppressWarnings("unchecked")
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (memoryCacheMap) {
            MapIterator itr = memoryCacheMap.mapIterator();

            deleteKey = new ArrayList<K>((memoryCacheMap.size() / 2) + 1);
            K key = null;
            MemoryCacheObject c = null;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (MemoryCacheObject) itr.getValue();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (memoryCacheMap) {
                memoryCacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}