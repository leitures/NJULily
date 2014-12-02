package dataservice.inventorydataservice;

import po.InventoryBillPO;
import dataenum.BillType;
import dataservice.CommonDataService;
import dataservice.TableInfoService;

/**
 * 提供库存单据的提交服务（赠送单、报溢单、报损单、报警单）
 * 保存库存盘点的批号
 * @author cylong
 * @version Oct 26, 2014 3:33:20 PM
 */
public interface InventoryDataService extends CommonDataService<InventoryBillPO> {

	/**
	 * @param type （赠送单、报溢单、报损单、报警单）
	 * @return 相应单据的ID
	 * @author cylong
	 * @version 2014年12月2日  下午5:53:27
	 */
	public String getID(BillType type);

	/**
	 * @return 返回一个盘点的批号
	 * @author cylong
	 * @version Nov 8, 2014 6:25:36 PM
	 */
	public String returnNumber();

	/**
	 * @return InventoryInfoService 的实例
	 * @author cylong
	 * @version 2014年12月2日 上午2:45:01
	 */
	public TableInfoService<InventoryBillPO> getInfo();
}
