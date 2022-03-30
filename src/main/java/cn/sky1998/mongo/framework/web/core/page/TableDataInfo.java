package cn.sky1998.mongo.framework.web.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author tcy@1753163342@qq.com
 */
public class TableDataInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 总记录数
   */
  private long total;

  /**
   * 当前记录数
   */
  private long count;

  /**
   * 列表数据
   */
  private List<?> datas;

  /**
   * 消息状态码
   */
  private int code;

  /**
   * 消息内容
   */
  private String msg;

  /**
   * 表格数据对象
   */
  public TableDataInfo() {
  }

  /**
   * 分页
   *
   * @param list  列表数据
   * @param total 总记录数
   */
  public TableDataInfo(List<?> list, int total) {
    this.datas = list;
    this.total = total;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public List<?> getDatas() {
    return datas;
  }

  public void setDatas(List<?> datas) {
    this.datas = datas;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
