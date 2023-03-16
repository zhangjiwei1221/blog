package cn.butterfly.flinkcdc.listener;

/**
 * 监听器接口
 *
 * @author zjw
 * @date 2023-03-14
 */
public interface FlinkCdcListener<T> {

    /**
     * 处理消息
     *
	 * @param message 消息
     */
    void onMessage(T message);

}
