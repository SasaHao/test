package com.letv.ssp.control;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import com.letv.ssp.pages.SSPAdPage;
import com.letv.ssp.test.BaseTest;
import com.letv.ssp.util.Constant;

public class SSPAdPageControl extends BaseTest {

	private SSPAdPage sspAdPage = new SSPAdPage();

	// 新建媒体
	public void mediaCreate(String publisher)
			throws Exception {
		driver.findElement(sspAdPage.mediaLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.newMediaLink).click();
		Thread.sleep(2000);
		Select publisherSelect = new Select(
				driver.findElement(sspAdPage.publishId));
		publisherSelect.selectByVisibleText(publisher);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.mediaName).sendKeys(mediaName);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.mediaSave).click();
		Thread.sleep(5000);
		if ((driver
				.findElement(By
						.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[2]/a"))
				.getText()).equals(mediaName)) {
			System.out.println("新建媒体成功，name：" + mediaName);
		} else {
			System.out.println("新建媒体失败");
		}

	}

	// 新建业务线
	public void linerCreate() throws Exception {
		String platForm = Constant.Plat_Form;
		driver.findElement(sspAdPage.linerLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.newLinerLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.newClientLiner).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.linerName).sendKeys(linerName);
		Thread.sleep(1000);
		Select mediaSelect = new Select(
				driver.findElement(sspAdPage.mediaName_liner));
		mediaSelect.selectByVisibleText(mediaName);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.platForm).sendKeys(platForm);
		Thread.sleep(1000);

		Select productSelect = new Select(
				driver.findElement(sspAdPage.cloudProduct));
		productSelect.selectByVisibleText("云点播");
		Thread.sleep(1000);
		Select categorySelect = new Select(
				driver.findElement(sspAdPage.cateGory));
		categorySelect.selectByVisibleText("综合门户");
		Thread.sleep(1000);
		Select pvlevelSelect = new Select(driver.findElement(sspAdPage.pvLevel));
		pvlevelSelect.selectByVisibleText("1万以下");
		Thread.sleep(1000);
		driver.findElement(sspAdPage.linerSave).click();
		Thread.sleep(5000);
		newLinerId = driver
				.findElement(
						By.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[1]"))
				.getText();
		if ((driver
				.findElement(By
						.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[2]/a"))
				.getText()).equals(linerName)) {
			System.out.println("新建业务线成功，name：" + linerName);
		} else {
			System.out.println("新建业务线失败");
		}
	}

	// 审核业务线
	public void checkLiner() throws Exception {
		driver.findElement(sspAdPage.checkLinerLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.linerNameCheck).sendKeys(linerName);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.searchLiner).click();
		Thread.sleep(1000);
		String checkPassNew = sspAdPage.checkPass + newLinerId;
		driver.findElement(By.id(checkPassNew)).click();
		Thread.sleep(5000);
		System.out.println("审核业务线成功");
	}

	// SSP新建广告位
	public void adzoneCreate(String adzoneType)
			throws Exception {
		driver.findElement(sspAdPage.adzoneLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.newAdzoneLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.adzoneName).sendKeys(sspAdzoneName);
		Thread.sleep(1000);
		Select belongLinerSelect = new Select(
				driver.findElement(sspAdPage.belongLiner));
		belongLinerSelect.selectByVisibleText(linerName);
		Thread.sleep(1000);
		Select adzoneTypeSelect = new Select(
				driver.findElement(sspAdPage.adzoneType));
		adzoneTypeSelect.selectByVisibleText(adzoneType);
		Thread.sleep(1000);
		if (adzoneType.equals("应用推荐")||adzoneType.equals("信息流")) {
			driver.findElement(sspAdPage.adzoneSave2).click();
		} else {
			driver.findElement(sspAdPage.adzoneSave1).click();
		}
		Thread.sleep(5000);
		newSSPAdzoneId = driver
				.findElement(
						By.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[1]"))
				.getText();
		if ((driver
				.findElement(By
						.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[2]/a"))
				.getText()).equals(sspAdzoneName)) {
			System.out.println("新建SSP广告位成功，name：" + sspAdzoneName);
		} else {
			System.out.println("新建SSP广告位失败");
		}
	}

	// 新建投放策略页面
	public void strategyCreate(String adzoneType) throws Exception {
		String platForm = Constant.Plat_Form;
		String adzoneTypeSelectnew = sspAdPage.adzoneTypeSelect1
				+ newSSPAdzoneId + sspAdPage.adzoneTypeSelect2;
		driver.findElement(sspAdPage.managerLink_strategy).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.adstrategyLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.newAdstrategyLink).click();
		Thread.sleep(2000);
		driver.findElement(sspAdPage.strategyName).sendKeys(strategyName);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.mediaName_strategy).sendKeys(mediaName);
		driver.findElement(sspAdPage.mediaNameSelect).click();
		Thread.sleep(1000);
		Select platformSelect = new Select(
				driver.findElement(sspAdPage.platForm_strategy));
		platformSelect.selectByVisibleText(platForm);
		Thread.sleep(1000);
		Select adzoneTypeSelect1 = new Select(
				driver.findElement(sspAdPage.adzoneType_strategy));
		adzoneTypeSelect1.selectByVisibleText(adzoneType);
		Thread.sleep(1000);
		driver.findElement(sspAdPage.adzoneTypeQuery).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(adzoneTypeSelectnew)).click();
		Thread.sleep(1000);
		driver.findElement(sspAdPage.adzoneTypeCheck).click();
		Thread.sleep(1000);
		driver.findElement(sspAdPage.areaSelect).click();
		Thread.sleep(1000);
		driver.findElement(sspAdPage.flowDistribute).click();
		Thread.sleep(1000);
		Select adServiceSystemSelect = new Select(
				driver.findElement(sspAdPage.adService));
		adServiceSystemSelect.selectByVisibleText("lecloud");
		Thread.sleep(1000);
		driver.findElement(sspAdPage.adPercent).sendKeys("100");
		Thread.sleep(1000);
		if (adzoneType.equals("前贴")) {
			driver.findElement(sspAdPage.adPercent15s).sendKeys("50");
			Thread.sleep(1000);
		}
		driver.findElement(sspAdPage.adPercentCheck).click();
		Thread.sleep(1000);
		if (adzoneType.equals("前贴")) {
			driver.findElement(sspAdPage.adDuration).sendKeys("60");
			Thread.sleep(1000);
		}
		driver.findElement(sspAdPage.strategySave).click();
		Thread.sleep(5000);
		if ((driver
				.findElement(By
						.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[2]"))
				.getText()).equals(strategyName)) {
			System.out.println("新建投放策略成功，name：" + strategyName);
		} else {
			System.out.println("新建投放策略失败");
		}

	}

	// 新建业务线后退出用户
	public void linerLogout() throws InterruptedException {
		driver.findElement(sspAdPage.linerLogout).click();
		Thread.sleep(5000);
	}

	// 审核业务线后退出系统
	public void checkLinerLogout() throws InterruptedException {
		driver.findElement(sspAdPage.checkLinerLogout).click();
		Thread.sleep(5000);
	}

}
