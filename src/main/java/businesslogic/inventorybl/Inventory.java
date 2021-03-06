package businesslogic.inventorybl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import po.InventoryBillPO;
import vo.InventoryBillVO;
import vo.InventoryCheckVO;
import vo.InventoryViewVO;
import config.RMIConfig;
import dataenum.BillState;
import dataenum.BillType;
import dataenum.ResultMessage;
import dataservice.inventorydataservice.InventoryDataService;

public class Inventory {

	private BillList list;
	private BillType type;
	private String ID;
	private InventoryDataService inventoryData;

	public Inventory() {
		inventoryData = getInventoryData();
	}

	/**
	 * 得到库存数据
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 6:11:29 PM
	 */
	public InventoryDataService getInventoryData() {
		try {
			return (InventoryDataService)Naming.lookup(RMIConfig.PREFIX + InventoryDataService.NAME);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用时间区间来查看这段时间内的销售／进货数量
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 6:11:42 PM
	 * @throws RemoteException 
	 */
	public InventoryViewVO viewInventory(String beginDate, String endDate) throws RemoteException {
		ViewList viewList = new ViewList(beginDate, endDate);
		InventoryViewVO vo = new InventoryViewVO(viewList.getSaleNumber(), viewList.getPurNumber(), viewList.getSaleMoney(), viewList.getPurMoney());
		return vo;
	}

	/**
	 * 库存盘点，返回库存盘点的单子
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 6:00:48 PM
	 * @throws RemoteException
	 */
	public InventoryCheckVO checkRecord() throws RemoteException {
		InventoryDataService inventoryData = getInventoryData();
		// 得到批号
		CheckList checkList = new CheckList(inventoryData.returnLotNumber());
		InventoryCheckVO vo = new InventoryCheckVO(checkList.getItemsVO(), checkList.getToday(), checkList.getLot());
		return vo;
	}

	/**
	 * 最开始要创建单据时，确定单据类型，返回单据ID
	 * @param type
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 7:17:13 PM
	 */
	private void setTpye(BillType type) {
		this.type = type;
		list = new BillList();
	}

	public String getGiftID() throws RemoteException {
		setTpye(BillType.GIFT);
		this.ID = inventoryData.getGiftID();
		return ID;
	}

	public String getOverFlowID() throws RemoteException {
		setTpye(BillType.OVERFLOW);
		this.ID = inventoryData.getOverflowID();
		return ID;
	}

	public String getLossID() throws RemoteException {
		setTpye(BillType.LOSS);
		this.ID = inventoryData.getLossID();
		return ID;
	}

	public String getAlarmID() throws RemoteException {
		setTpye(BillType.ALARM);
		this.ID = inventoryData.getAlarmID();
		return ID;
	}

	/**
	 * 往单子里添加商品
	 * @param ID
	 * @param number
	 * @author Zing
	 * @version Dec 2, 2014 6:11:55 PM
	 * @throws RemoteException 
	 */
	public void addCommodity(String ID, int number) throws RemoteException {
		BillListItem item = new BillListItem(ID, number);
		list.addItem(item);
	}

	/**
	 * 提交单子
	 * @param remark
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 6:11:58 PM
	 * @throws RemoteException
	 */
	public InventoryBillVO submit(String remark) throws RemoteException {
		list.setRemark(remark);
		InventoryBillPO po = getInventoryBill();
		inventoryData.insert(po);
		return InventoryTrans.poToVo(po);
	}

	/**
	 * 保存为草稿
	 * @param remark
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 6:12:02 PM
	 * @throws RemoteException 
	 */
	public InventoryBillVO save(String remark) throws RemoteException {
		list.setRemark(remark);
		InventoryBillPO po = getInventoryBill();
		po.setState(BillState.DRAFT);
		inventoryData.insert(po);
		return InventoryTrans.poToVo(po);
	}
	
	public ResultMessage updateDraft(InventoryBillVO vo) throws RemoteException {
		InventoryBillPO po = InventoryTrans.VOtoPO(vo);
		return inventoryData.update(po);
	}

	public ResultMessage submitDraft(String ID) throws RemoteException {
		InventoryBillPO po = inventoryData.find(ID);
		po.setState(BillState.APPROVALING);
		return inventoryData.update(po);
	}

	/**
	 * 建立起一个库存单据（赠送单、报警单、报溢单、报损单）
	 * @return
	 * @author Zing
	 * @version Dec 2, 2014 5:34:33 PM
	 */
	private InventoryBillPO getInventoryBill() {
		InventoryBillPO po = new InventoryBillPO(ID, type, list.getCommodityPOs(), list.getRemark());
		return po;
	}


}
