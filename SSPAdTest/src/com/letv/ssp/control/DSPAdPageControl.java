package com.letv.ssp.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.letv.ssp.pages.DSPAdPage;
import com.letv.ssp.test.BaseTest;
import com.letv.ssp.util.Constant;

public class DSPAdPageControl extends BaseTest {

	private DSPAdPage dspAdPage = new DSPAdPage();

	// 新建DSP广告位
	public void adzoneCreate(String lecloudAdtype, String adMimetype)
			throws Exception {
		String PlatForm = Constant.Plat_Form;
		Thread.sleep(2000);
		if (lecloudAdtype.equals("信息流")) {
			driver.findElement(dspAdPage.managerLink).click();
			Thread.sleep(2000);
		}
		driver.findElement(dspAdPage.adzoneLink).click();
		Thread.sleep(2000);
		driver.findElement(dspAdPage.newAdzoneLink).click();
		Thread.sleep(2000);
		driver.findElement(dspAdPage.adzoneName).sendKeys(dspadzoneName);
		Thread.sleep(1000);
		driver.findElement(dspAdPage.belongMedia).sendKeys(mediaName);
		Thread.sleep(1000);
		java.util.List<WebElement> medias = driver
				.findElements(dspAdPage.selectMedia);
		WebElement media = medias.get(0);
		media.click();
		driver.findElement(dspAdPage.rollType).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.rollTypeInput).sendKeys(lecloudAdtype);
		Thread.sleep(1000);
		java.util.List<WebElement> productLines = driver
				.findElements(dspAdPage.rollTypeSelect);
		WebElement productLine = productLines.get(0);
		productLine.click();
		Thread.sleep(1000);
		if (lecloudAdtype.equals("信息流")) {
			Select feedSelect = new Select(
					driver.findElement(dspAdPage.feedAdType));
			feedSelect.selectByVisibleText(adMimetype);
			Thread.sleep(1000);
		}
		if (lecloudAdtype.equals("应用推荐") || lecloudAdtype.equals("信息流")) {
		} else {
			driver.findElement(dspAdPage.productLiner);
			Thread.sleep(1000);
			driver.findElement(dspAdPage.productLinerSelect).sendKeys("云点播");
			java.util.List<WebElement> products = driver
					.findElements(dspAdPage.productLinerCheck);
			WebElement product = products.get(0);
			product.click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.platForm).sendKeys(PlatForm);
			Thread.sleep(1000);
			driver.findElement(dspAdPage.platFormSelect).click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.SSPAdMimetype).click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.SSPAdMimetype).sendKeys(adMimetype);
			Thread.sleep(1000);
			java.util.List<WebElement> mimetypes = driver
					.findElements(dspAdPage.SSPAdMimetypeSelect);
			WebElement mimetype = mimetypes.get(0);
			mimetype.click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.SSPAdMarxK).sendKeys("10000");
			Thread.sleep(1000);
		}
		driver.findElement(dspAdPage.SSPAdzone).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.SSPAdzoneName).sendKeys(sspAdzoneName);
		Thread.sleep(1000);
		driver.findElement(dspAdPage.SSPAdzoneSelect).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.SSPAdzoneCheck).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.lecloudAdzoneSave).click();
		Thread.sleep(5000);
		if ((driver
				.findElement(By
						.xpath("//html/body/div[1]/div[2]/section/div[2]/div[2]/table/tbody/tr[1]/td[2]/a"))
				.getText()).equals(dspadzoneName)) {
			System.out.println("新建广告位成功，name：" + dspadzoneName);
		} else {
			System.out.println("新建广告位失败");
		}
	}

	// 新建广告计划
	public void adplanCreate(String adzoneType, String openType, String showType)
			throws Exception {
		String adRangeEndDate;
		Thread.sleep(2000);
		driver.findElement(dspAdPage.adplanLink).click();
		Thread.sleep(2000);
		driver.findElement(dspAdPage.newAdplanLink).click();
		Thread.sleep(2000);
		if (openType.equals("应用推广")) {
			driver.findElement(dspAdPage.adopentype1).click();
			Thread.sleep(1000);
		}
		if (openType.equals("H5推广")) {
			driver.findElement(dspAdPage.adopentype2).click();
			Thread.sleep(1000);
		}
		if (openType.equals("电话推广")) {
			driver.findElement(dspAdPage.adopentype3).click();
			Thread.sleep(1000);
		}
		if (adzoneType.equals("前贴")) {
			driver.findElement(dspAdPage.adRolltype1).click();
			Thread.sleep(1000);
		}
		if (adzoneType.equals("暂停")) {
			driver.findElement(dspAdPage.adRolltype2).click();
			Thread.sleep(1000);
		}
		if (adzoneType.equals("应用推荐")) {
			driver.findElement(dspAdPage.adRolltype3).click();
			Thread.sleep(1000);
		}
		if (adzoneType.equals("信息流")) {
			driver.findElement(dspAdPage.adRolltype4).click();
			Thread.sleep(1000);
		}
		java.util.List<WebElement> adzonesid = driver
				.findElements(dspAdPage.adzoneName_adplan);
		WebElement adzoneid = adzonesid.get(adzonesid.size() - 1);
		adzoneid.click();
		Thread.sleep(1000);
		if (adzoneType.equals("应用推荐")) {
			driver.findElement(dspAdPage.appName).sendKeys("微信");
			Thread.sleep(2000);
			driver.findElement(dspAdPage.appSelect).click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.appName).clear();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.appName).sendKeys("微信创意测试001");
			Thread.sleep(1000);
		}
		if (adzoneType.equals("信息流")) {
			if (showType.equals("小图")) {
				driver.findElement(dspAdPage.letitleSmall).click();
				Thread.sleep(1000);
			}
			if (showType.equals("大图")) {
				driver.findElement(dspAdPage.letitleBig).click();
				Thread.sleep(1000);
			}
			if (showType.equals("组图")) {
				driver.findElement(dspAdPage.letitleGroup).click();
				Thread.sleep(1000);
			}
			if (showType.equals("乐见")) {
				driver.findElement(dspAdPage.leviewBig).click();
				Thread.sleep(1000);
			}
			driver.findElement(dspAdPage.feedCreativeName).sendKeys(
					feedCreativeName);
			Thread.sleep(1000);
			if (showType.equals("乐见")) {
				driver.findElement(dspAdPage.leviewFeedFile).sendKeys(
						"C:\\Users\\pictures\\乐见1.jpg");
				Thread.sleep(2000);
			} else {
				driver.findElement(dspAdPage.feedFile).sendKeys(
						"C:\\Users\\pictures\\小图1.jpg");
				Thread.sleep(2000);
			}
			driver.findElement(dspAdPage.creativeTitle).sendKeys(
					feedCreativeTitle);
			Thread.sleep(1000);
			driver.findElement(dspAdPage.creativeSource).sendKeys(
					feedCreativeSource);
			Thread.sleep(1000);
			driver.findElement(dspAdPage.landingPage).sendKeys(
					"http://www.baidu.com");
			Thread.sleep(1000);
			if (openType.equals("电话推广")) {
				Select teleSelect = new Select(
						driver.findElement(dspAdPage.dialType));
				teleSelect.selectByVisibleText("立即拨打");
				Thread.sleep(1000);
				driver.findElement(dspAdPage.mobile).sendKeys("10086");
				Thread.sleep(1000);
			}
		} else {
			driver.findElement(dspAdPage.adCreative).click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.adCreativeType).sendKeys(adzoneType);
			Thread.sleep(1000);
			java.util.List<WebElement> adcreatives = driver
					.findElements(dspAdPage.adcreativeSelect);
			WebElement adcreative = adcreatives.get(0);
			adcreative.click();
			Thread.sleep(1000);
		}
		driver.findElement(dspAdPage.nextStep).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.adplanGroup).click();
		Thread.sleep(1000);
		java.util.List<WebElement> adplans = driver
				.findElements(dspAdPage.adplanGroupSelect);
		WebElement adplan = adplans.get(1);
		adplan.click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.adplanName).sendKeys(dspadplanName);
		Thread.sleep(1000);
		if (adzoneType.equals("应用推荐") || adzoneType.equals("信息流")) {
			driver.findElement(dspAdPage.storeAdplanRange).click();
			Thread.sleep(1000);
		} else {
			driver.findElement(dspAdPage.adplanRange).click();
			Thread.sleep(1000);
		}
		// 创建30天排期的广告计划：获取30天后日期
		DSPAdPageControl data = new DSPAdPageControl();
		long nowDate = System.currentTimeMillis();
		adRangeEndDate = data.afterNumDay(new Date(nowDate), 29);
		driver.findElement(dspAdPage.adRangeEnd).clear();
		driver.findElement(dspAdPage.adRangeEnd).sendKeys(adRangeEndDate);
		Thread.sleep(1000);
		driver.findElement(dspAdPage.adplanDate).click();
		Thread.sleep(1000);
		if (adzoneType.equals("应用推荐") || adzoneType.equals("信息流")) {
			driver.findElement(dspAdPage.nextStep).click();
			Thread.sleep(1000);
			driver.findElement(dspAdPage.adPrice).sendKeys("6");
			Thread.sleep(1000);
		} else {
			driver.findElement(dspAdPage.adplanTotal).sendKeys("30");
			Thread.sleep(1000);
			driver.findElement(dspAdPage.adplanPercent).click();
			Thread.sleep(1000);

		}
		driver.findElement(dspAdPage.lecloudAdplanSave).click();
		Thread.sleep(5000);
		if (adzoneType.equals("应用推荐") || adzoneType.equals("信息流")) {
			if ((driver.findElement(By.xpath("//*/td[5]/a[1]")).getText())
					.equals(dspadplanName)) {
				System.out.println("新建广告计划成功，name：" + dspadplanName);
			} else {
				System.out.println("新建广告计划失败");
			}
		} else {
			if ((driver.findElement(By
					.xpath("//*[@id='orders']/tr[1]/td[3]/a[1]")).getText())
					.equals(dspadplanName)) {
				System.out.println("新建广告计划成功，name：" + dspadplanName);
			} else {
				System.out.println("新建广告计划失败");
			}
		}
	}

	// 审核创意通过
	public void creativeCheck() throws InterruptedException {
		driver.findElement(dspAdPage.creativeLink).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.creativeCheckLink).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.creativeCheck).click();
		Thread.sleep(1000);
		Select applevelSelect = new Select(
				driver.findElement(dspAdPage.appLevel));
		applevelSelect.selectByVisibleText("S级");
		Thread.sleep(1000);
		driver.findElement(dspAdPage.saveAppLevel).click();
		Thread.sleep(2000);
		System.out.println("审核创意成功");
	}

	// 获取当前日期之后30天的日期
	public String afterNumDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, day);
		return new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
	}

	// 审核SSP广告位
	public void checkSSPAdzone() throws Exception {
		driver.findElement(dspAdPage.managerLink).click();
		Thread.sleep(2000);
		driver.findElement(dspAdPage.sspAdzoneLink).click();
		Thread.sleep(2000);
		driver.findElement(dspAdPage.sspAdzoneName).sendKeys(sspAdzoneName);
		Thread.sleep(1000);
		driver.findElement(dspAdPage.queryAdzoneName).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.clickAdzoneName).click();
		Thread.sleep(1000);
		Select checklevelSelect = new Select(
				driver.findElement(dspAdPage.sspAdzoneCheck));
		checklevelSelect.selectByVisibleText("S级");
		Thread.sleep(1000);
		Select appTypeSelect = new Select(
				driver.findElement(dspAdPage.sspAdzoneType));
		appTypeSelect.selectByVisibleText("软件");
		Thread.sleep(1000);
		driver.findElement(dspAdPage.sspAdzoneCheckSave).click();
		Thread.sleep(5000);
		System.out.println("SSP广告位评级成功");
	}

	// 退出用户（评级，新建广告位后退出）
	public void logout_store() throws InterruptedException {
		driver.findElement(dspAdPage.handlers).click();
		Thread.sleep(1000);
		driver.findElement(dspAdPage.logout).click();
		Thread.sleep(2000);
	}

	// 关闭浏览器
	public void closeDriver() throws InterruptedException {
		driver.quit();
	}

}
