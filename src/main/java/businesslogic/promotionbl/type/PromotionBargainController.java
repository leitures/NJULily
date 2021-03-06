package businesslogic.promotionbl.type;

import java.rmi.RemoteException;
import java.util.ArrayList;

import log.LogMsgController;
import vo.InventoryBillVO;
import vo.commodity.CommodityItemVO;
import vo.promotion.PromotionBargainVO;
import blservice.promotionblservice.PromotionBargainBLService;

/**
 * @see blservice.promotionblservice.PromotionBargainBLService
 * @author cylong
 * @version 2014年12月14日 下午5:08:50
 */
public class PromotionBargainController implements PromotionBargainBLService {

	private PromotionBargain promotionBargain;

	public PromotionBargainController() {
		promotionBargain = new PromotionBargain();
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#show()
	 */
	@Override
	public ArrayList<PromotionBargainVO> show() {
		try {
			return promotionBargain.show();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#showGifts()
	 */
	@Override
	public ArrayList<InventoryBillVO> showGifts() {
		try {
			return promotionBargain.showGifts();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#getID()
	 */
	@Override
	public String getID() {
		try {
			return promotionBargain.getID();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#addBargain(vo.commodity.CommodityItemVO)
	 */
	@Override
	public void addBargain(CommodityItemVO vo) {
		try {
			promotionBargain.addBargain(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#setBargainTotalPrice(double)
	 */
	@Override
	public void setBargainTotalPrice(double price) {
		promotionBargain.setBargainTotalPrice(price);
	}

	/**
	 * @see blservice.promotionblservice.PromotionBargainBLService#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public PromotionBargainVO submit(String beginDate, String endDate) {
		try {
			PromotionBargainVO vo = promotionBargain.submit(beginDate, endDate);
			if(vo == null)
				return null;
			LogMsgController.addLog("添加特价包促销策略 "  + vo.toString());
			return vo;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
