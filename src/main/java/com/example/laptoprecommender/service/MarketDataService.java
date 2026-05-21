package com.example.laptoprecommender.service;

import com.example.laptoprecommender.model.Laptop;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketDataService {

    // Reusable image URLs by category
    private static final String IMG_MAC  = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400&h=300&fit=crop";
    private static final String IMG_GAME = "https://images.unsplash.com/photo-1593642632559-0c6d3fc62b89?w=400&h=300&fit=crop";
    private static final String IMG_ULTRA = "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400&h=300&fit=crop";
    private static final String IMG_STD  = "https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=400&h=300&fit=crop";
    private static final String IMG_WORK = "https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=400&h=300&fit=crop";

    private List<Laptop> laptopCache;

    public MarketDataService() {
        System.out.println("[MarketData] Loading Indian market laptop database...");
        this.laptopCache = buildDatabase();
        assignBenchmarks(this.laptopCache);
        System.out.println("[MarketData] Loaded " + laptopCache.size() + " laptops. Price range: ₹25K – ₹10L.");
    }

    public Laptop findById(int id) {
        for (Laptop l : laptopCache) {
            if (l.getId() == id) return copy(l);
        }
        return null;
    }

    public List<Laptop> fetchLaptops() {
        List<Laptop> fresh = new ArrayList<>();
        for (Laptop src : laptopCache) fresh.add(copy(src));
        return fresh;
    }

    // ==========================================================================================
    //  COMPACT FACTORY — one call per laptop
    // ==========================================================================================
    private void add(List<Laptop> L, int id, String title, String desc,
                     double priceINR, double rating, String thumb,
                     int ram, String cpu, String gpu,
                     String dSize, String dType, String chip,
                     String tier, String gen, String gpuM, String stor,
                     String res, int hz, int uA, int uC, boolean hdmi, boolean sd,
                     String spk, String bld, double wt, double bat) {
        Laptop l = new Laptop();
        l.setId(id); l.setTitle(title); l.setDescription(desc);
        l.setPrice(priceINR); l.setRating(rating); l.setThumbnail(thumb);
        l.setRamGB(ram); l.setCpu(cpu); l.setGpu(gpu);
        l.setDisplaySize(dSize); l.setDisplayType(dType); l.setChipsetFamily(chip);
        l.setCpuTier(tier); l.setCpuGeneration(gen); l.setGpuModel(gpuM);
        l.setStorageCapacity(stor); l.setResolution(res); l.setRefreshRate(hz);
        l.setUsbACount(uA); l.setUsbCCount(uC); l.setHdmiPort(hdmi); l.setSdCardSlot(sd);
        l.setSpeakerQuality(spk); l.setBuildMaterial(bld); l.setWeight(wt); l.setBatteryLife(bat);
        L.add(l);
    }

    private List<Laptop> buildDatabase() {
        List<Laptop> L = new ArrayList<>();
        int id = 1;

        // =====================================================================================
        //   BUDGET ( ₹25,000 – ₹45,000 )  —  30 models
        // =====================================================================================

        add(L, id++, "Infinix INBook Y2 Plus",
            "Ultra-budget with Intel i5 and aluminum body.",
            25990, 3.8, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Aluminum",1.58,8);

        add(L, id++, "Lenovo IdeaPad Slim 1 15AMN7",
            "Ultra-affordable slim laptop for students.",
            26990, 3.9, IMG_STD, 8, "AMD Ryzen 3 7320U", "AMD Radeon Graphics",
            "15-16","IPS","AMD","Ryzen 3","Zen 2","Radeon Integrated","256GB",
            "FHD",60,1,1,true,false,"Basic","Plastic",1.63,9);

        add(L, id++, "Acer Extensa 15 EX215-55",
            "No-frills workhorse for basic computing.",
            27490, 3.8, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.7,7);

        add(L, id++, "Samsung Galaxy Book Go",
            "Samsung's entry-level lightweight laptop.",
            27999, 3.7, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.58,8);

        add(L, id++, "Acer Aspire Go 15 A515-57G",
            "Budget 15-inch for school and everyday use.",
            27990, 3.8, IMG_STD, 8, "Intel Core i3-N305", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.78,7);

        add(L, id++, "HP 15s-eq2669AU",
            "Reliable everyday laptop with Ryzen performance.",
            28990, 4.0, IMG_STD, 8, "AMD Ryzen 3 5300U", "AMD Radeon Graphics",
            "15-16","IPS","AMD","Ryzen 3","Zen 3","Radeon Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.69,8);

        add(L, id++, "Nokia PureBook S14",
            "Clean design with stock Windows experience.",
            29990, 3.8, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "13-14","IPS","Intel","i3","12th Gen","UHD Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Aluminum",1.4,8);

        add(L, id++, "Lenovo V15 Gen 4 AMD",
            "Business-ready budget laptop with reliable build.",
            29990, 4.0, IMG_STD, 8, "AMD Ryzen 3 7320U", "AMD Radeon Graphics",
            "15-16","IPS","AMD","Ryzen 3","Zen 2","Radeon Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.65,8);

        add(L, id++, "ASUS Vivobook 15 X1502",
            "Lightweight 15-inch with NanoEdge bezels.",
            31990, 4.1, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.7,7);

        add(L, id++, "Dell Inspiron 15 3520",
            "Classic Inspiron for home and office.",
            32990, 4.0, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.65,7);

        add(L, id++, "HP 15s-fq5330TU",
            "Popular student laptop with FHD anti-glare display.",
            33990, 4.0, IMG_STD, 8, "Intel Core i3-1215U", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","12th Gen","UHD Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.69,8);

        add(L, id++, "Xiaomi RedmiBook 15",
            "Xiaomi budget laptop with clean, fast interface.",
            33990, 4.0, IMG_STD, 8, "Intel Core i3-1115G4", "Intel UHD Graphics",
            "15-16","IPS","Intel","i3","11th Gen","UHD Integrated","256GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.8,8);

        add(L, id++, "Infinix INBook X2 Gen 11",
            "Compact 14-inch with Intel 11th Gen on a budget.",
            34990, 3.9, IMG_STD, 8, "Intel Core i5-1155G7", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","11th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Aluminum",1.48,8);

        add(L, id++, "ASUS Vivobook Go 15 E1504",
            "Value AMD laptop with large FHD display.",
            35990, 4.0, IMG_STD, 8, "AMD Ryzen 5 7520U", "AMD Radeon Graphics",
            "15-16","IPS","AMD","Ryzen 5","Zen 2","Radeon Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.63,8);

        add(L, id++, "HP 250 G9",
            "HP business budget laptop with solid IO.",
            35990, 3.9, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.74,7);

        add(L, id++, "Colorful EVOL Y15",
            "Colorful budget gaming-style laptop.",
            36990, 3.8, IMG_STD, 8, "Intel Core i5-12450H", "Intel UHD Graphics",
            "15-16","IPS","Intel","i5","12th Gen","UHD Integrated","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.75,6);

        add(L, id++, "Lenovo IdeaPad Slim 3 15IRU8",
            "Slim affordable all-rounder with Ryzen 5.",
            36990, 4.1, IMG_STD, 8, "AMD Ryzen 5 7520U", "AMD Radeon Graphics",
            "15-16","IPS","AMD","Ryzen 5","Zen 2","Radeon Integrated","512GB",
            "FHD",60,1,1,true,true,"Basic","Plastic",1.63,9);

        add(L, id++, "Huawei MateBook D 15 (2024)",
            "Huawei's affordable ultrabook with FullView display.",
            37990, 4.1, IMG_ULTRA, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,1,2,false,false,"Basic","Aluminum",1.56,8);

        add(L, id++, "Acer Aspire 5 A515-58M",
            "Step-up budget with i5 for multitasking.",
            38990, 4.1, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.76,8);

        add(L, id++, "Nokia PureBook X14 HSN",
            "Premium budget 14-inch with i5 power.",
            38990, 4.0, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Aluminum",1.4,9);

        add(L, id++, "Dell Inspiron 14 3420",
            "Compact 14-inch Dell for everyday use.",
            39990, 4.0, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.55,8);

        add(L, id++, "Realme Book Prime",
            "Value aluminum ultrabook with 2K IPS display.",
            39999, 4.2, IMG_ULTRA, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "QHD",60,0,2,false,false,"Good","Aluminum",1.37,10);

        add(L, id++, "Xiaomi Notebook Pro 120G",
            "Xiaomi premium-feel budget with 120Hz display.",
            39990, 4.2, IMG_ULTRA, 8, "Intel Core i5-12450H", "Intel UHD Graphics",
            "13-14","IPS","Intel","i5","12th Gen","UHD Integrated","512GB",
            "FHD",120,1,2,true,false,"Good","Aluminum",1.4,9);

        add(L, id++, "HP 14s-dq5138tu",
            "Compact 14-inch i5 laptop for mobile productivity.",
            41990, 4.1, IMG_STD, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.46,8);

        add(L, id++, "Huawei MateBook D 14 (2024)",
            "Huawei 14-inch FullView with eye-comfort display.",
            42990, 4.2, IMG_ULTRA, 8, "Intel Core i5-1240P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,1,2,false,false,"Good","Aluminum",1.38,10);

        add(L, id++, "Samsung Galaxy Book3 15",
            "Samsung's value laptop with stunning AMOLED option.",
            42990, 4.1, IMG_STD, 8, "Intel Core i5-1335U", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.58,8);

        add(L, id++, "Lenovo ThinkBook 15 Gen 4+",
            "Business smart with dual-tone aluminum design.",
            43990, 4.2, IMG_STD, 16, "Intel Core i5-12500H", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Good","Aluminum",1.7,8);

        add(L, id++, "ASUS Vivobook 14 X1404VA",
            "Premium feel 14-inch at an accessible price.",
            43990, 4.1, IMG_STD, 8, "Intel Core i5-1335U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.4,8);

        add(L, id++, "MSI Modern 14 C12MO",
            "MSI's entry into thin-and-light business laptops.",
            44990, 4.1, IMG_ULTRA, 8, "Intel Core i5-1235U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","12th Gen","Iris Xe","512GB",
            "FHD",60,1,1,true,false,"Basic","Aluminum",1.4,8);

        add(L, id++, "Gigabyte G5 KE",
            "Gigabyte's budget entry into gaming laptops.",
            44990, 4.0, IMG_GAME, 8, "Intel Core i5-12500H", "NVIDIA RTX 3060 6GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 3060","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",2.2,5);

        // =====================================================================================
        //   MID-RANGE ( ₹45,000 – ₹75,000 )  —  40 models
        // =====================================================================================

        add(L, id++, "MSI Modern 14 C13M",
            "Sleek and light for professionals on the go.",
            46990, 4.2, IMG_ULTRA, 16, "Intel Core i5-1335U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Aluminum",1.4,9);

        add(L, id++, "Acer Aspire 5 A514-56M",
            "14-inch mid-range with 13th Gen Intel.",
            47990, 4.2, IMG_STD, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.53,9);

        add(L, id++, "Colorful EVOL P15",
            "Colorful gaming laptop with RTX graphics.",
            48990, 4.0, IMG_GAME, 16, "Intel Core i5-12450H", "NVIDIA GTX 1650 4GB",
            "15-16","IPS","Intel","i5","12th Gen","GTX 1650","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",2.1,5);

        add(L, id++, "Lenovo IdeaPad Slim 5i 14IAH8",
            "Slim 14-inch with excellent keyboard and display.",
            48990, 4.3, IMG_ULTRA, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Good","Aluminum",1.46,10);

        add(L, id++, "HP Victus 15 (GTX 1650)",
            "Entry-level gaming laptop with GTX graphics.",
            49990, 4.2, IMG_GAME, 8, "AMD Ryzen 5 5600H", "NVIDIA GTX 1650 4GB",
            "15-16","IPS","AMD","Ryzen 5","Zen 3","GTX 1650","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",2.37,5);

        add(L, id++, "Dell Vostro 14 3430",
            "Business-focused 14-inch with reliable build.",
            49990, 4.1, IMG_STD, 16, "Intel Core i5-1335U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Basic","Plastic",1.48,8);

        add(L, id++, "Lenovo IdeaPad Slim 5 14ABR8",
            "AMD Ryzen powered slim with excellent battery.",
            49990, 4.3, IMG_ULTRA, 16, "AMD Ryzen 5 7530U", "AMD Radeon Graphics",
            "13-14","IPS","AMD","Ryzen 5","Zen 3+","Radeon Integrated","512GB",
            "FHD",60,1,1,true,false,"Good","Aluminum",1.39,11);

        add(L, id++, "Gigabyte G5 MF5",
            "Gigabyte value gaming with RTX 4050.",
            49990, 4.2, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4050","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",2.2,5);

        add(L, id++, "Huawei MateBook 14 (2024)",
            "Huawei's 2K touch display ultrabook.",
            51990, 4.3, IMG_ULTRA, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "QHD",60,1,2,false,false,"Good","Aluminum",1.49,10);

        add(L, id++, "HP Pavilion 14-dv2014TU",
            "Stylish 14-inch with premium feel.",
            52990, 4.3, IMG_ULTRA, 16, "Intel Core i5-1335U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,false,"Good","Aluminum",1.41,9);

        add(L, id++, "Lenovo LOQ 15IAX9",
            "Budget gaming with dedicated RTX graphics.",
            52990, 4.2, IMG_GAME, 8, "Intel Core i5-12450HX", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 3050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.38,5);

        add(L, id++, "MSI Bravo 15 D7UDX",
            "MSI AMD gaming with solid performance.",
            53990, 4.2, IMG_GAME, 16, "AMD Ryzen 5 7535HS", "AMD Radeon RX 6500M 4GB",
            "15-16","IPS","AMD","Ryzen 5","Zen 3+","RX 6500M","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",2.3,5);

        add(L, id++, "ASUS Vivobook S 14 OLED M3402",
            "Gorgeous OLED display at a mid-range price.",
            54990, 4.4, IMG_ULTRA, 16, "AMD Ryzen 5 7530U", "AMD Radeon Graphics",
            "13-14","OLED/Mini-LED","AMD","Ryzen 5","Zen 3+","Radeon Integrated","512GB",
            "QHD",90,1,1,true,false,"Good","Aluminum",1.4,10);

        add(L, id++, "Lenovo IdeaPad Flex 5 14ALC7",
            "Versatile 2-in-1 convertible with touchscreen.",
            54990, 4.3, IMG_STD, 8, "AMD Ryzen 5 5500U", "AMD Radeon Graphics",
            "13-14","IPS","AMD","Ryzen 5","Zen 2","Radeon Integrated","512GB",
            "FHD",60,1,1,false,false,"Basic","Plastic",1.55,10);

        add(L, id++, "Acer Nitro V 15 ANV15-51",
            "Affordable gaming with 13th Gen Intel and RTX.",
            55990, 4.2, IMG_GAME, 8, "Intel Core i5-13420H", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i5","13th Gen","RTX 3050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.1,5);

        add(L, id++, "Infinix Zero Book 13",
            "Infinix premium ultrabook with OLED display.",
            55990, 4.1, IMG_ULTRA, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i5","13th Gen","Iris Xe","512GB",
            "QHD",60,1,2,false,false,"Good","Aluminum",1.2,10);

        add(L, id++, "Dell Inspiron 14 5430",
            "Well-rounded 14-inch with 13th Gen performance.",
            55990, 4.3, IMG_ULTRA, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,true,"Good","Aluminum",1.53,10);

        add(L, id++, "Dell Inspiron 15 5530",
            "15-inch productivity with solid build quality.",
            57990, 4.3, IMG_WORK, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "15-16","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,1,true,true,"Good","Aluminum",1.65,10);

        add(L, id++, "Lenovo ThinkPad E14 Gen 5",
            "Business-grade durability with best-in-class keyboard.",
            58990, 4.5, IMG_ULTRA, 16, "Intel Core i5-1340P", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,2,true,false,"Good","Aluminum",1.41,12);

        add(L, id++, "Gigabyte G6 KF",
            "Gigabyte 16-inch gaming with RTX 4060.",
            58990, 4.3, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4060","512GB",
            "FHD",165,3,1,true,false,"Basic","Plastic",2.3,5);

        add(L, id++, "MSI Thin 15 B13UCX",
            "MSI thin gaming with RTX 3050.",
            59990, 4.1, IMG_GAME, 16, "Intel Core i5-13420H", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i5","13th Gen","RTX 3050","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",1.86,6);

        add(L, id++, "Samsung Galaxy Book3 360",
            "Samsung 2-in-1 with S-Pen support and AMOLED.",
            59990, 4.3, IMG_ULTRA, 16, "Intel Core i5-1335U", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,0,2,true,false,"Good","Aluminum",1.16,11);

        add(L, id++, "Xiaomi Notebook Pro 14",
            "Xiaomi 2.8K 120Hz premium notebook.",
            62990, 4.3, IMG_ULTRA, 16, "Intel Core i5-13500H", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "QHD",120,1,2,true,false,"Good","Aluminum",1.4,9);

        add(L, id++, "ASUS Zenbook 14 UM3402YA",
            "Compact OLED ultrabook with AMD Ryzen 7.",
            62990, 4.5, IMG_ULTRA, 16, "AMD Ryzen 7 7730U", "AMD Radeon Graphics",
            "13-14","OLED/Mini-LED","AMD","Ryzen 7","Zen 3+","Radeon Integrated","512GB",
            "QHD",90,1,2,true,false,"Good","Aluminum",1.39,10);

        add(L, id++, "Colorful X16 AT",
            "Colorful 16-inch gaming with RTX 4050.",
            64990, 4.1, IMG_GAME, 16, "Intel Core i7-12650H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i7","12th Gen","RTX 4050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.3,5);

        add(L, id++, "HP Pavilion Plus 14-ey0047TU",
            "Creator-friendly with 2.8K OLED and premium audio.",
            64990, 4.4, IMG_ULTRA, 16, "Intel Core i5-13500H", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i5","13th Gen","Iris Xe","512GB",
            "QHD",90,1,2,true,true,"Premium","Aluminum",1.39,9);

        add(L, id++, "MSI Cyborg 15 A12VE",
            "MSI entry gaming with future-ready design.",
            65990, 4.2, IMG_GAME, 16, "Intel Core i5-12450H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4050","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",1.98,5);

        add(L, id++, "Acer Aspire 7 A715-76G",
            "Acer mid-range with RTX graphics for creators.",
            64990, 4.2, IMG_WORK, 16, "Intel Core i5-12450H", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 3050","512GB",
            "FHD",60,2,1,true,false,"Good","Plastic",2.15,7);

        add(L, id++, "Huawei MateBook 14s",
            "Huawei premium with 2.5K touch and 90Hz.",
            66990, 4.4, IMG_ULTRA, 16, "Intel Core i5-13500H", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "QHD",90,1,2,false,false,"Good","Aluminum",1.43,10);

        add(L, id++, "HP Envy x360 14-es0013dx",
            "Premium convertible with OLED touchscreen.",
            67990, 4.4, IMG_ULTRA, 16, "AMD Ryzen 5 7530U", "AMD Radeon Graphics",
            "13-14","OLED/Mini-LED","AMD","Ryzen 5","Zen 3+","Radeon Integrated","512GB",
            "QHD",60,2,1,true,false,"Good","Aluminum",1.39,10);

        add(L, id++, "Dell Latitude 5440",
            "Enterprise workhorse with vPro-capable chip.",
            67990, 4.3, IMG_WORK, 16, "Intel Core i5-1345U", "Intel Iris Xe",
            "13-14","IPS","Intel","i5","13th Gen","Iris Xe","512GB",
            "FHD",60,2,2,true,false,"Good","Aluminum",1.39,10);

        add(L, id++, "ASUS TUF Gaming F15 FX507ZC4",
            "Durable gaming with military-spec chassis.",
            67990, 4.3, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 3050 4GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 3050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.2,6);

        add(L, id++, "Lenovo Legion Slim 5 14AHP9",
            "Slim gaming profile with strong performance.",
            69990, 4.4, IMG_GAME, 16, "AMD Ryzen 7 7840HS", "NVIDIA RTX 4050 6GB",
            "13-14","IPS","AMD","Ryzen 7","Zen 4","RTX 4050","512GB",
            "QHD",120,2,2,true,false,"Good","Aluminum",1.8,8);

        add(L, id++, "HP Victus 16 (RTX 4050)",
            "Gaming with 16-inch screen and RTX 4050.",
            69990, 4.3, IMG_GAME, 16, "Intel Core i5-13420H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i5","13th Gen","RTX 4050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.3,5);

        add(L, id++, "MSI Sword 15 A12VE",
            "MSI gaming sword with strong GPU pairing.",
            69990, 4.3, IMG_GAME, 16, "Intel Core i7-12650H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i7","12th Gen","RTX 4050","512GB",
            "FHD",144,2,1,true,false,"Basic","Plastic",2.25,5);

        add(L, id++, "ASUS Vivobook Pro 15 OLED",
            "15-inch OLED creator with MX graphics.",
            72990, 4.4, IMG_WORK, 16, "AMD Ryzen 7 7730U", "NVIDIA MX550 2GB",
            "15-16","OLED/Mini-LED","AMD","Ryzen 7","Zen 3+","MX550","512GB",
            "QHD",60,2,1,true,false,"Good","Aluminum",1.65,9);

        add(L, id++, "Gigabyte AORUS 15 9MF",
            "Gigabyte AORUS entry with RTX 4060.",
            74990, 4.3, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4060","512GB",
            "FHD",165,3,1,true,false,"Good","Aluminum",2.25,5);

        add(L, id++, "Acer Predator Helios Neo 16",
            "Acer flagship mid-range gaming laptop.",
            74990, 4.3, IMG_GAME, 16, "Intel Core i5-13500HX", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i5","13th Gen","RTX 4050","512GB",
            "QHD",165,3,1,true,false,"Good","Plastic",2.35,5);

        // =====================================================================================
        //   PREMIUM ( ₹75,000 – ₹1,20,000 )  —  45 models
        // =====================================================================================

        add(L, id++, "Acer Nitro 5 AN515-58",
            "Popular mid-tier gaming with RTX 3050 and 144Hz.",
            75990, 4.3, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 3050 4GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 3050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.5,5);

        add(L, id++, "Gigabyte G5 MF5 (RTX 4060)",
            "Value gaming with RTX 4060 at aggressive price.",
            76990, 4.3, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4060","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.35,5);

        add(L, id++, "MSI Katana GF66 12UE",
            "MSI gaming with 12th Gen and RTX 3060.",
            77990, 4.2, IMG_GAME, 16, "Intel Core i7-12650H", "NVIDIA RTX 3060 6GB",
            "15-16","IPS","Intel","i7","12th Gen","RTX 3060","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.25,5);

        add(L, id++, "Colorful EVOL X15 AT 24",
            "Colorful high-performance gaming with RTX 4060.",
            79990, 4.2, IMG_GAME, 16, "Intel Core i5-12500H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i5","12th Gen","RTX 4060","512GB",
            "FHD",165,3,1,true,false,"Basic","Plastic",2.2,5);

        add(L, id++, "ASUS Zenbook 14 OLED UX3405 (2024)",
            "Ultra-slim Zen 4 ultrabook with vivid OLED.",
            79990, 4.6, IMG_ULTRA, 16, "AMD Ryzen 7 8840U", "AMD Radeon 780M",
            "13-14","OLED/Mini-LED","AMD","Ryzen 7","Zen 4","Radeon 780M Integrated","512GB",
            "QHD",120,1,2,true,false,"Good","Aluminum",1.28,12);

        add(L, id++, "ASUS TUF Gaming A16 FA617NS (2024)",
            "Newer TUF with Ryzen 7 and RTX 4050.",
            79990, 4.4, IMG_GAME, 16, "AMD Ryzen 7 7735HS", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","AMD","Ryzen 7","Zen 3+","RTX 4050","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.2,6);

        add(L, id++, "Lenovo Yoga Slim 7i 14IAL7",
            "Thin and light with 2.8K OLED and Thunderbolt 4.",
            82990, 4.5, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",90,0,2,false,false,"Good","Aluminum",1.29,12);

        add(L, id++, "MSI Katana 15 B13VFK",
            "Solid mid-range gaming with RTX 4060.",
            82990, 4.4, IMG_GAME, 16, "Intel Core i7-13620H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.25,6);

        add(L, id++, "Samsung Galaxy Book3 Pro",
            "Ultra-slim AMOLED with incredible 0.87kg weight.",
            84990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",120,0,2,true,false,"Premium","Aluminum",0.87,13);

        add(L, id++, "ASUS TUF Gaming A15 FA507NV",
            "Durable gaming with military-grade toughness.",
            84990, 4.5, IMG_GAME, 16, "AMD Ryzen 7 7735HS", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","AMD","Ryzen 7","Zen 3+","RTX 4060","512GB",
            "FHD",144,3,1,true,false,"Basic","Plastic",2.35,6);

        add(L, id++, "Dell Inspiron 16 Plus 7630",
            "Large-screen productivity with discrete graphics.",
            85990, 4.3, IMG_WORK, 16, "Intel Core i7-13700H", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 3050","512GB",
            "QHD",60,2,1,true,true,"Good","Aluminum",1.87,8);

        add(L, id++, "Huawei MateBook X Pro (2024)",
            "Huawei flagship with 3K OLED and ultra-thin profile.",
            86990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",120,0,2,false,false,"Premium","Magnesium",1.26,12);

        add(L, id++, "Apple MacBook Air 13 M2",
            "Fanless ultrabook with exceptional efficiency.",
            89900, 4.8, IMG_MAC, 8, "Apple M2 8-Core", "Apple 8-Core GPU",
            "13-14","IPS","Apple","M2","M-Series","Apple 8-Core Integrated","256GB",
            "QHD",60,0,2,false,false,"Good","Aluminum",1.24,18);

        add(L, id++, "Dell XPS 13 Plus 9320",
            "Minimalist luxury ultrabook with edge-to-edge keyboard.",
            89990, 4.5, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "FHD",60,0,2,false,false,"Good","Aluminum",1.17,12);

        add(L, id++, "HP Pavilion Plus 16-ab0033TX",
            "Large 16-inch with RTX 3050 for creative work.",
            89990, 4.3, IMG_WORK, 16, "Intel Core i7-13700H", "NVIDIA RTX 3050 6GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 3050","512GB",
            "QHD",60,2,1,true,true,"Good","Aluminum",1.79,8);

        add(L, id++, "ASUS Vivobook S 14 OLED (2024)",
            "Latest 14th Gen Intel in a slim OLED ultrabook.",
            89990, 4.5, IMG_ULTRA, 16, "Intel Core i5-14600H", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i5","14th Gen","Iris Xe","512GB",
            "QHD",120,1,2,true,false,"Good","Aluminum",1.39,10);

        add(L, id++, "Lenovo ThinkBook 16p Gen 4",
            "Creator-focused business laptop with QHD+ display.",
            89990, 4.4, IMG_WORK, 16, "AMD Ryzen 7 7840HS", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","AMD","Ryzen 7","Zen 4","RTX 4050","512GB",
            "QHD",120,2,2,true,true,"Good","Aluminum",1.9,8);

        add(L, id++, "LG Gram 14 14Z90RS",
            "Featherweight 14-inch productivity powerhouse.",
            92990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Good","Magnesium",0.99,14);

        add(L, id++, "MSI Prestige 16 AI Studio",
            "MSI creator ultrabook with AI accelerator.",
            93990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4050","512GB",
            "QHD",60,2,2,true,true,"Good","Aluminum",1.8,9);

        add(L, id++, "Acer Swift X 14 SFX14-72G",
            "Creator ultrabook with RTX and OLED display.",
            94990, 4.5, IMG_ULTRA, 16, "AMD Ryzen 7 7840U", "NVIDIA RTX 4050 6GB",
            "13-14","OLED/Mini-LED","AMD","Ryzen 7","Zen 4","RTX 4050","512GB",
            "QHD",120,2,2,true,false,"Good","Aluminum",1.4,10);

        add(L, id++, "HP Omen 15-ek2065TX",
            "Gaming laptop with i5 and RTX 4060.",
            95990, 4.4, IMG_GAME, 16, "Intel Core i5-13500HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i5","13th Gen","RTX 4060","512GB",
            "QHD",165,3,1,true,false,"Good","Plastic",2.37,5);

        add(L, id++, "Gigabyte AERO 14 OLED",
            "Gigabyte creator laptop with 4K OLED.",
            96990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","512GB",
            "QHD",120,2,1,true,true,"Good","Aluminum",1.49,10);

        add(L, id++, "Apple MacBook Air 13 M3",
            "Latest M3 chip in the iconic Air design.",
            99900, 4.8, IMG_MAC, 8, "Apple M3 8-Core", "Apple 10-Core GPU",
            "13-14","IPS","Apple","M3","M-Series","Apple 10-Core Integrated","256GB",
            "QHD",60,0,2,false,false,"Good","Aluminum",1.24,18);

        add(L, id++, "Lenovo Legion 5 15IAH7",
            "AMD-powered gaming with RTX 4060 and 165Hz.",
            99990, 4.5, IMG_GAME, 16, "AMD Ryzen 7 7745HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","AMD","Ryzen 7","Zen 4","RTX 4060","512GB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.5,6);

        add(L, id++, "Microsoft Surface Laptop 5",
            "Premium touchscreen ultrabook with Alcantara deck.",
            99990, 4.5, IMG_ULTRA, 16, "Intel Core i7-1255U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","12th Gen","Iris Xe","512GB",
            "QHD",60,1,1,false,false,"Premium","Aluminum",1.27,15);

        add(L, id++, "Dell XPS 14 9440",
            "Dell's new 14-inch XPS with OLED option.",
            99990, 4.5, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",120,0,2,false,false,"Premium","Aluminum",1.46,12);

        add(L, id++, "ASUS Zenbook 14X OLED",
            "ASUS premium OLED with wide color gamut.",
            99990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",120,1,2,true,false,"Good","Aluminum",1.4,10);

        add(L, id++, "MSI Prestige 14 A12SC",
            "MSI slim business laptop with Thunderbolt.",
            99990, 4.4, IMG_ULTRA, 16, "Intel Core i7-1360P", "NVIDIA RTX 4050 6GB",
            "13-14","IPS","Intel","i7","13th Gen","RTX 4050","512GB",
            "QHD",60,1,2,false,false,"Good","Aluminum",1.29,10);

        add(L, id++, "Samsung Galaxy Book4 Ultra",
            "Samsung flagship with RTX 4050 and AMOLED.",
            104990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","1TB",
            "QHD",120,1,2,true,false,"Premium","Aluminum",1.85,9);

        add(L, id++, "Acer Predator Helios 16 (RTX 4060)",
            "Acer gaming flagship with RTX 4060.",
            109990, 4.5, IMG_GAME, 16, "Intel Core i7-13700HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.5,5);

        add(L, id++, "HP Envy 16-h1023TX",
            "HP premium 16-inch with RTX for creators.",
            109990, 4.5, IMG_WORK, 16, "Intel Core i7-13700H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",120,2,2,true,true,"Premium","Aluminum",2.07,8);

        add(L, id++, "Lenovo Yoga Pro 9i 16IMH9",
            "Premium creator 2-in-1 with Mini-LED display.",
            109990, 4.6, IMG_ULTRA, 32, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","1TB",
            "QHD",120,2,2,true,true,"Premium","Aluminum",2.0,10);

        add(L, id++, "Gigabyte AORUS 15X ASF",
            "Gigabyte AORUS 15 with RTX 4060 and QHD.",
            109990, 4.4, IMG_GAME, 16, "Intel Core i7-13700HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.4,5);

        add(L, id++, "Huawei MateBook X Pro Ultra",
            "Huawei's top-end ultrabook with OLED touchscreen.",
            109990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","1TB",
            "QHD",120,0,2,false,false,"Premium","Magnesium",1.26,13);

        add(L, id++, "MSI Sword 17 HX B14VFKG",
            "MSI 17-inch gaming with RTX 4060.",
            109990, 4.4, IMG_GAME, 16, "Intel Core i7-14700HX", "NVIDIA RTX 4060 8GB",
            "17+","IPS","Intel","i7","14th Gen","RTX 4060","1TB",
            "QHD",165,3,1,true,false,"Good","Plastic",2.7,5);

        add(L, id++, "Lenovo ThinkPad X1 Carbon Gen 11",
            "Legendary business ultrabook with MIL-SPEC.",
            109990, 4.7, IMG_ULTRA, 16, "Intel Core i7-1365U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Good","Magnesium",1.12,15);

        add(L, id++, "Microsoft Surface Laptop Studio 2",
            "Microsoft's creator convertible with RTX graphics.",
            114990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "13-14","IPS","Intel","i7","13th Gen","RTX 4050","512GB",
            "QHD",120,2,1,true,false,"Premium","Aluminum",1.89,13);

        add(L, id++, "Apple MacBook Air 15 M3",
            "Big 15-inch MacBook Air with M3 chip.",
            114900, 4.8, IMG_MAC, 8, "Apple M3 8-Core", "Apple 10-Core GPU",
            "15-16","IPS","Apple","M3","M-Series","Apple 10-Core Integrated","256GB",
            "QHD",60,0,2,false,false,"Premium","Aluminum",1.51,18);

        add(L, id++, "MSI Creator M14 HX C14VFK",
            "MSI compact creator with RTX 4050.",
            114990, 4.5, IMG_WORK, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","1TB",
            "QHD",120,2,2,true,true,"Good","Aluminum",1.7,8);

        add(L, id++, "ASUS Zenbook Pro 14 OLED",
            "Pro-grade OLED ultrabook with RTX.",
            114990, 4.6, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","1TB",
            "QHD",120,1,2,true,false,"Good","Aluminum",1.56,10);

        add(L, id++, "HP EliteBook 840 G10",
            "Enterprise-grade laptop with vPro and Sure View.",
            119990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1365U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "FHD",60,2,2,true,false,"Good","Aluminum",1.34,14);

        add(L, id++, "LG Gram 16 16Z90RS",
            "LG's 16-inch featherweight with WQXGA display.",
            119990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "15-16","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Good","Magnesium",1.19,15);

        add(L, id++, "Gigabyte AERO 16 OLED",
            "Gigabyte creator flagship with 4K OLED.",
            119990, 4.5, IMG_WORK, 16, "Intel Core i7-13700H", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4060","1TB",
            "4K",60,2,2,true,true,"Good","Aluminum",2.0,8);

        // =====================================================================================
        //   HIGH-END ( ₹1,20,000 – ₹2,00,000 )  —  40 models
        // =====================================================================================

        add(L, id++, "Lenovo Legion 5 Pro Gen 8",
            "AMD gaming beast with gorgeous QHD+ 165Hz.",
            124990, 4.6, IMG_GAME, 16, "AMD Ryzen 7 7745HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","AMD","Ryzen 7","Zen 4","RTX 4060","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.5,6);

        add(L, id++, "MSI Raider GE66 15HX",
            "MSI Raider gaming with RTX 4060.",
            124990, 4.5, IMG_GAME, 16, "Intel Core i7-12700H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","12th Gen","RTX 4060","1TB",
            "QHD",165,3,1,true,false,"Good","Aluminum",2.38,5);

        add(L, id++, "ASUS ROG Zephyrus G14 GA403",
            "Compact gaming powerhouse with OLED and AMD.",
            129990, 4.7, IMG_GAME, 16, "AMD Ryzen 9 7940HS", "NVIDIA RTX 4060 8GB",
            "13-14","OLED/Mini-LED","AMD","Ryzen 9","Zen 4","RTX 4060","1TB",
            "QHD",120,2,2,true,false,"Good","Magnesium",1.72,10);

        add(L, id++, "Acer Predator Helios 16 PH16-71",
            "Powerful gaming with i7 and RTX 4060.",
            129990, 4.5, IMG_GAME, 16, "Intel Core i7-13700HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.6,5);

        add(L, id++, "Lenovo Yoga 9i Gen 8 14IAP7",
            "Premium 2-in-1 with rotating soundbar and 4K OLED.",
            129990, 4.7, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","1TB",
            "4K",60,1,2,false,false,"Premium","Aluminum",1.37,14);

        add(L, id++, "Dell Latitude 7440",
            "Dell enterprise ultrabook with long battery.",
            129990, 4.5, IMG_ULTRA, 16, "Intel Core i7-1365U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Good","Aluminum",1.34,15);

        add(L, id++, "Gigabyte AORUS 15X ASF (RTX 4070)",
            "Gigabyte 15-inch with RTX 4070 gaming power.",
            132990, 4.5, IMG_GAME, 16, "Intel Core i7-13700HX", "NVIDIA RTX 4070 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.4,5);

        add(L, id++, "MSI Creator Z16P B13VHPT",
            "Creator workstation with Mini-LED touchscreen.",
            134990, 4.5, IMG_WORK, 32, "Intel Core i9-13900H", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4060","1TB",
            "QHD",120,2,2,true,true,"Premium","Aluminum",2.0,8);

        add(L, id++, "Colorful EVOL X16 Pro",
            "Colorful flagship gaming with RTX 4060.",
            134990, 4.2, IMG_GAME, 16, "Intel Core i7-13700H", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",165,3,1,true,false,"Basic","Plastic",2.3,5);

        add(L, id++, "Dell XPS 15 9530",
            "15-inch powerhouse with OLED InfinityEdge.",
            139990, 4.7, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","512GB",
            "QHD",60,0,2,false,true,"Premium","Aluminum",1.86,10);

        add(L, id++, "MSI Raider GE68 HX 14VHG",
            "High-performance gaming with 14th Gen and RTX 4060.",
            139990, 4.5, IMG_GAME, 16, "Intel Core i7-14700HX", "NVIDIA RTX 4060 8GB",
            "15-16","IPS","Intel","i7","14th Gen","RTX 4060","1TB",
            "QHD",165,3,1,true,false,"Good","Aluminum",2.4,5);

        add(L, id++, "LG Gram 17 17Z90RS",
            "Incredibly light 17-inch productivity powerhouse.",
            139990, 4.7, IMG_ULTRA, 16, "Intel Core i7-1360P", "Intel Iris Xe",
            "17+","IPS","Intel","i7","13th Gen","Iris Xe","1TB",
            "QHD",60,2,2,true,false,"Good","Magnesium",1.35,14);

        add(L, id++, "Samsung Galaxy Book3 Ultra 16",
            "Samsung's RTX-powered flagship AMOLED.",
            139990, 4.5, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4050","1TB",
            "QHD",120,1,2,true,false,"Premium","Aluminum",1.79,8);

        add(L, id++, "HP Omen 16 (2024)",
            "Latest gaming with 14th Gen Intel and RTX 4070.",
            144990, 4.6, IMG_GAME, 16, "Intel Core i7-14700HX", "NVIDIA RTX 4070 8GB",
            "15-16","IPS","Intel","i7","14th Gen","RTX 4070","1TB",
            "QHD",165,3,1,true,true,"Good","Plastic",2.38,5);

        add(L, id++, "Apple MacBook Pro 14 M3",
            "Pro performance with Mini-LED XDR display.",
            149900, 4.9, IMG_MAC, 8, "Apple M3 8-Core", "Apple 10-Core GPU",
            "13-14","OLED/Mini-LED","Apple","M3","M-Series","Apple 10-Core Integrated","512GB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",1.55,17);

        add(L, id++, "ASUS ROG Strix G16 G614JV (2024)",
            "Flagship gaming with 14th Gen and RTX 4070.",
            149990, 4.7, IMG_GAME, 16, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "15-16","IPS","Intel","i9","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.5,5);

        add(L, id++, "HP Spectre x360 14-eu0050TU",
            "Gem-cut convertible with OLED and pen support.",
            149990, 4.6, IMG_ULTRA, 16, "Intel Core i7-1355U", "Intel Iris Xe",
            "13-14","OLED/Mini-LED","Intel","i7","13th Gen","Iris Xe","1TB",
            "QHD",60,0,2,false,false,"Premium","Aluminum",1.36,13);

        add(L, id++, "Lenovo ThinkPad X1 Carbon Gen 12",
            "Latest ThinkPad X1 with 14th Gen Intel.",
            149990, 4.7, IMG_ULTRA, 16, "Intel Core i7-14650U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","14th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Good","Magnesium",1.08,15);

        add(L, id++, "Dell XPS 16 9640",
            "Dell's new 16-inch XPS with OLED.",
            149990, 4.6, IMG_ULTRA, 32, "Intel Core i7-14700H", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","Intel","i7","14th Gen","RTX 4060","1TB",
            "QHD",120,0,2,false,true,"Premium","Aluminum",2.0,10);

        add(L, id++, "MSI Titan 18 HX 14VIG",
            "MSI Titan gaming beast with RTX 4070.",
            149990, 4.6, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "17+","IPS","Intel","i9","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,true,"Good","Aluminum",3.2,4);

        add(L, id++, "Acer Predator Helios 18",
            "Acer 18-inch gaming with RTX 4070.",
            154990, 4.5, IMG_GAME, 16, "Intel Core i7-14700HX", "NVIDIA RTX 4070 8GB",
            "17+","IPS","Intel","i7","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",3.0,5);

        add(L, id++, "ASUS ROG Flow X16 GV601VV",
            "Convertible gaming with 165Hz Mini-LED touchscreen.",
            159990, 4.5, IMG_GAME, 16, "AMD Ryzen 9 7940HS", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","AMD","Ryzen 9","Zen 4","RTX 4060","1TB",
            "QHD",165,2,2,true,true,"Good","Magnesium",2.0,8);

        add(L, id++, "HP EliteBook 860 G10",
            "Enterprise ultrabook with privacy features.",
            159990, 4.5, IMG_ULTRA, 32, "Intel Core i7-1365U", "Intel Iris Xe",
            "15-16","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "FHD",60,2,2,true,false,"Good","Aluminum",1.78,14);

        add(L, id++, "Gigabyte AORUS 17X ANG",
            "17-inch AORUS with RTX 4070.",
            159990, 4.5, IMG_GAME, 16, "Intel Core i7-13700HX", "NVIDIA RTX 4070 8GB",
            "17+","IPS","Intel","i7","13th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,true,"Good","Aluminum",2.7,5);

        add(L, id++, "Dell Precision 5680",
            "Dell mobile workstation for CAD and engineering.",
            164990, 4.6, IMG_WORK, 32, "Intel Core i7-13800H", "NVIDIA RTX A2000 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX A2000","1TB",
            "QHD",60,2,2,true,true,"Good","Aluminum",1.91,9);

        add(L, id++, "ASUS ProArt Studiobook 16 H7604JI",
            "ISV-certified creator workstation with OLED.",
            169990, 4.6, IMG_WORK, 32, "Intel Core i9-13980HX", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4060","1TB",
            "QHD",60,2,2,true,true,"Good","Aluminum",2.4,8);

        add(L, id++, "Dell XPS 17 9730",
            "17-inch ultrabook with InfinityEdge 4K display.",
            169990, 4.6, IMG_ULTRA, 16, "Intel Core i7-13700H", "NVIDIA RTX 4050 6GB",
            "17+","IPS","Intel","i7","13th Gen","RTX 4050","512GB",
            "4K",60,0,2,false,true,"Premium","Aluminum",2.44,9);

        add(L, id++, "HP ZBook Firefly 16 G10",
            "HP mobile workstation for designers.",
            169990, 4.5, IMG_WORK, 32, "Intel Core i7-1365U", "NVIDIA RTX A500 4GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX A500","512GB",
            "QHD",60,2,2,true,true,"Good","Aluminum",1.8,12);

        add(L, id++, "MSI Creator Z17 HX Studio",
            "MSI 17-inch creator with RTX 4060.",
            174990, 4.5, IMG_WORK, 32, "Intel Core i7-13700HX", "NVIDIA RTX 4060 8GB",
            "17+","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4060","1TB",
            "QHD",120,2,2,true,true,"Premium","Aluminum",2.5,7);

        add(L, id++, "HP Dragonfly G4",
            "HP elite convertible with 5G option.",
            179990, 4.7, IMG_ULTRA, 16, "Intel Core i7-1365U", "Intel Iris Xe",
            "13-14","IPS","Intel","i7","13th Gen","Iris Xe","512GB",
            "QHD",60,2,2,true,false,"Premium","Magnesium",0.99,14);

        add(L, id++, "Lenovo ThinkPad P16 Gen 2",
            "Professional mobile workstation for CAD.",
            189990, 4.6, IMG_WORK, 32, "Intel Core i7-13850HX", "NVIDIA RTX A2000 8GB",
            "15-16","IPS","Intel","i7","13th Gen","RTX A2000","1TB",
            "QHD",60,2,2,true,true,"Good","Magnesium",2.8,7);

        add(L, id++, "Apple MacBook Pro 14 M3 Pro",
            "Pro performance with M3 Pro for multi-threaded work.",
            189900, 4.9, IMG_MAC, 18, "Apple M3 Pro 12-Core", "Apple 18-Core GPU",
            "13-14","OLED/Mini-LED","Apple","M3 Pro","M-Series","Apple 18-Core Integrated","512GB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",1.55,17);

        add(L, id++, "MSI Creator Z16P",
            "MSI creator flagship with RTX 4060.",
            189990, 4.5, IMG_WORK, 32, "Intel Core i9-13900H", "NVIDIA RTX 4060 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4060","1TB",
            "QHD",120,2,2,true,true,"Premium","Aluminum",2.0,8);

        add(L, id++, "Samsung Galaxy Book3 Ultra",
            "Samsung flagship with AMOLED and RTX 4050.",
            194990, 4.5, IMG_ULTRA, 32, "Intel Core i9-13900H", "NVIDIA RTX 4050 6GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4050","1TB",
            "QHD",120,1,2,true,false,"Premium","Aluminum",1.79,8);

        add(L, id++, "ASUS ROG Strix G18 G814JVR",
            "18-inch gaming behemoth with RTX 4070.",
            189990, 4.6, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "17+","IPS","Intel","i9","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",3.0,5);

        add(L, id++, "Razer Blade 14 (2024)",
            "Razer's compact 14-inch gaming with RTX 4070.",
            189990, 4.6, IMG_GAME, 16, "AMD Ryzen 9 7940HS", "NVIDIA RTX 4070 8GB",
            "13-14","IPS","AMD","Ryzen 9","Zen 4","RTX 4070","1TB",
            "QHD",165,2,2,true,false,"Good","Aluminum",1.84,8);

        add(L, id++, "Alienware m16 R2",
            "Dell Alienware gaming with RTX 4070.",
            194990, 4.6, IMG_GAME, 16, "Intel Core i7-14700HX", "NVIDIA RTX 4070 8GB",
            "15-16","IPS","Intel","i7","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.7,5);

        add(L, id++, "Lenovo Legion Pro 7i Gen 9",
            "Flagship Legion with i9 and RTX 4070.",
            199990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "15-16","IPS","Intel","i9","14th Gen","RTX 4070","1TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.5,5);

        // =====================================================================================
        //   ULTRA PREMIUM ( ₹2,00,000 – ₹5,00,000 )  —  30 models
        // =====================================================================================

        add(L, id++, "Apple MacBook Pro 16 M3 Pro",
            "Ultimate portable workstation with XDR display.",
            219900, 4.9, IMG_MAC, 18, "Apple M3 Pro 12-Core", "Apple 18-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M3 Pro","M-Series","Apple 18-Core Integrated","512GB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.14,22);

        add(L, id++, "Lenovo ThinkPad X1 Extreme Gen 5",
            "Power meets portability in ThinkPad design.",
            219990, 4.7, IMG_ULTRA, 32, "Intel Core i7-13700H", "NVIDIA RTX 4070 8GB",
            "15-16","OLED/Mini-LED","Intel","i7","13th Gen","RTX 4070","1TB",
            "4K",60,2,2,true,true,"Good","Magnesium",1.87,9);

        add(L, id++, "Dell Precision 7680",
            "Dell enterprise workstation with Pro GPU.",
            229990, 4.6, IMG_WORK, 32, "Intel Core i9-13950HX", "NVIDIA RTX A3000 12GB",
            "15-16","IPS","Intel","i9","13th Gen","RTX A3000","1TB",
            "QHD",60,2,2,true,true,"Good","Aluminum",2.7,7);

        add(L, id++, "MSI Titan 18 HX (RTX 4080)",
            "MSI Titan desktop replacement with RTX 4080.",
            239990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4080 12GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4080","2TB",
            "QHD",165,3,2,true,true,"Premium","Aluminum",3.2,4);

        add(L, id++, "ASUS ROG Zephyrus M16 (2024)",
            "ASUS ultra-slim gaming with RTX 4070.",
            239990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4070","1TB",
            "QHD",165,2,2,true,false,"Good","Aluminum",2.0,7);

        add(L, id++, "HP ZBook Studio G10",
            "Premium mobile workstation with DreamColor display.",
            249990, 4.6, IMG_WORK, 32, "Intel Core i9-13900H", "NVIDIA RTX 4070 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4070","1TB",
            "4K",60,2,2,true,true,"Premium","Aluminum",1.73,9);

        add(L, id++, "Gigabyte AORUS 17X AZG (2024)",
            "17-inch gaming with mechanical keyboard and RTX 4080.",
            259990, 4.6, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4080 12GB",
            "17+","IPS","Intel","i9","14th Gen","RTX 4080","2TB",
            "QHD",165,3,2,true,true,"Good","Aluminum",3.0,4);

        add(L, id++, "MSI Raider GE78 HX 14VIG",
            "Desktop replacement gaming with 240Hz display.",
            269990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4080 12GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4080","2TB",
            "QHD",165,3,2,true,true,"Premium","Aluminum",3.1,4);

        add(L, id++, "ASUS ROG Strix SCAR 17 G733PYV",
            "E-sports champion with Ryzen 9 and RTX 4080.",
            279990, 4.7, IMG_GAME, 32, "AMD Ryzen 9 7945HX", "NVIDIA RTX 4080 12GB",
            "17+","IPS","AMD","Ryzen 9","Zen 4","RTX 4080","2TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",2.7,5);

        add(L, id++, "Alienware m17 R5",
            "Alienware 17-inch with RTX 4080 and Cryo-tech.",
            279990, 4.6, IMG_GAME, 32, "AMD Ryzen 9 7945HX", "NVIDIA RTX 4080 12GB",
            "17+","IPS","AMD","Ryzen 9","Zen 4","RTX 4080","2TB",
            "QHD",165,3,2,true,true,"Good","Aluminum",3.0,4);

        add(L, id++, "Apple MacBook Pro 14 M3 Max",
            "Maximum Apple performance for pro video and 3D work.",
            299900, 4.9, IMG_MAC, 36, "Apple M3 Max 16-Core", "Apple 40-Core GPU",
            "13-14","OLED/Mini-LED","Apple","M3 Max","M-Series","Apple 40-Core Integrated","1TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",1.61,14);

        add(L, id++, "Razer Blade 16 (2024) RTX 4080",
            "Premium CNC gaming with RTX 4080.",
            299990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4080 12GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4080","2TB",
            "4K",120,2,2,true,true,"Premium","Aluminum",2.4,5);

        add(L, id++, "Lenovo Legion 9i Gen 9 16IRX9",
            "Top-tier gaming with liquid cooling and 4K Mini-LED.",
            329990, 4.8, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "4K",165,3,3,true,true,"Premium","Aluminum",2.56,4);

        add(L, id++, "Apple MacBook Pro 16 M3 Max",
            "The ultimate Apple powerhouse with 16-inch XDR display.",
            329900, 4.9, IMG_MAC, 36, "Apple M3 Max 16-Core", "Apple 40-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M3 Max","M-Series","Apple 40-Core Integrated","1TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.14,22);

        add(L, id++, "MSI Titan GT77 HX 14VIG",
            "MSI's absolute gaming flagship with RTX 4090.",
            339990, 4.7, IMG_GAME, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "4K",120,3,2,true,true,"Premium","Aluminum",3.5,4);

        add(L, id++, "ASUS ROG Strix SCAR 18 G834JYR",
            "Extreme 18-inch gaming with RTX 4090 and 240Hz.",
            349990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "QHD",165,3,2,true,false,"Good","Aluminum",3.1,4);

        add(L, id++, "Razer Blade 16 (2024) RTX 4090",
            "Premium CNC gaming with RTX 4090.",
            349990, 4.7, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "4K",120,2,2,true,true,"Premium","Aluminum",2.4,5);

        add(L, id++, "ASUS ROG Zephyrus Duo 16 GX650PY",
            "Dual-screen gaming innovation with ScreenPad Plus.",
            379990, 4.6, IMG_GAME, 32, "AMD Ryzen 9 7945HX", "NVIDIA RTX 4090 16GB",
            "15-16","OLED/Mini-LED","AMD","Ryzen 9","Zen 4","RTX 4090","2TB",
            "QHD",165,2,2,true,false,"Good","Aluminum",2.7,5);

        add(L, id++, "Gigabyte AORUS 17X (RTX 4090)",
            "Gigabyte 17-inch flagship with RTX 4090.",
            389990, 4.6, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","IPS","Intel","i9","14th Gen","RTX 4090","2TB",
            "QHD",165,3,2,true,true,"Good","Aluminum",3.0,4);

        add(L, id++, "Apple MacBook Pro 16 M3 Max (48GB)",
            "Maximum memory config for heaviest creative workflows.",
            399900, 4.9, IMG_MAC, 48, "Apple M3 Max 16-Core", "Apple 40-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M3 Max","M-Series","Apple 40-Core Integrated","1TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.14,22);

        add(L, id++, "Razer Blade 18 (2024)",
            "18-inch desktop replacement with RTX 4090.",
            399990, 4.6, IMG_GAME, 32, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "4K",165,3,2,true,true,"Premium","Aluminum",2.9,4);

        add(L, id++, "ASUS ProArt Studiobook Pro 16 W7604",
            "ISV-certified workstation with RTX 4000 Ada.",
            399990, 4.6, IMG_WORK, 64, "Intel Core i9-13980HX", "NVIDIA RTX 4000 Ada 12GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4000 Ada","2TB",
            "4K",60,2,2,true,true,"Good","Aluminum",2.4,8);

        add(L, id++, "Lenovo ThinkPad P1 Gen 6",
            "Slim workstation with RTX 4090 and 4K OLED.",
            429990, 4.7, IMG_WORK, 64, "Intel Core i9-13900H", "NVIDIA RTX 4090 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 4090","2TB",
            "4K",60,2,2,true,true,"Good","Magnesium",1.86,8);

        add(L, id++, "Dell Precision 5690",
            "Dell next-gen precision workstation.",
            439990, 4.7, IMG_WORK, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4070 8GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4070","2TB",
            "4K",120,2,2,true,true,"Premium","Aluminum",1.86,9);

        add(L, id++, "Alienware m18 R2",
            "Dell's gaming flagship with quad-fan cooling.",
            449990, 4.6, IMG_GAME, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","IPS","Intel","i9","14th Gen","RTX 4090","4TB",
            "QHD",165,3,3,true,true,"Premium","Aluminum",3.87,4);

        add(L, id++, "HP ZBook Fury 16 G10",
            "HP top-end mobile workstation.",
            459990, 4.6, IMG_WORK, 64, "Intel Core i9-13950HX", "NVIDIA RTX A5000 16GB",
            "15-16","IPS","Intel","i9","13th Gen","RTX A5000","2TB",
            "4K",60,3,2,true,true,"Good","Aluminum",2.8,6);

        add(L, id++, "MSI Titan 18 HX (RTX 4090/64GB)",
            "MSI ultimate desktop replacement.",
            469990, 4.7, IMG_GAME, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","4TB",
            "4K",120,3,2,true,true,"Premium","Aluminum",3.5,4);

        add(L, id++, "ASUS ROG Strix SCAR 18 (Ultimate)",
            "Ultimate SCAR configuration with 4TB storage.",
            489990, 4.7, IMG_GAME, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "17+","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","4TB",
            "QHD",165,3,3,true,true,"Premium","Aluminum",3.1,4);

        add(L, id++, "Alienware x16 R2 (RTX 4090)",
            "Alienware premium slim gaming with RTX 4090.",
            499990, 4.7, IMG_GAME, 64, "Intel Core i9-14900HX", "NVIDIA RTX 4090 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","14th Gen","RTX 4090","2TB",
            "4K",120,3,2,true,true,"Premium","Aluminum",2.75,5);

        // =====================================================================================
        //   EXTREME WORKSTATION ( ₹5,00,000 – ₹10,00,000 )  —  15 models
        // =====================================================================================

        add(L, id++, "Dell Precision 7780",
            "Enterprise workstation with RTX 5000 Ada and ECC.",
            529990, 4.7, IMG_WORK, 64, "Intel Core i9-13950HX", "NVIDIA RTX 5000 Ada 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX 5000 Ada","2TB",
            "4K",60,2,2,true,true,"Good","Aluminum",2.8,7);

        add(L, id++, "HP ZBook Fury 17 G10",
            "17-inch dream workstation with DreamColor display.",
            529990, 4.6, IMG_WORK, 64, "Intel Core i9-13950HX", "NVIDIA RTX A5000 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX A5000","2TB",
            "4K",60,3,2,true,true,"Good","Aluminum",3.0,6);

        add(L, id++, "Lenovo ThinkPad P16 Gen 2 (Max)",
            "Maximum-spec ThinkPad P16 workstation.",
            569990, 4.7, IMG_WORK, 64, "Intel Core i9-13980HX", "NVIDIA RTX A5000 16GB",
            "15-16","IPS","Intel","i9","13th Gen","RTX A5000","2TB",
            "4K",60,2,2,true,true,"Good","Magnesium",2.8,7);

        add(L, id++, "Apple MacBook Pro 16 M2 Ultra",
            "Apple's most powerful chip in its most capable form factor.",
            599900, 4.9, IMG_MAC, 64, "Apple M2 Ultra 24-Core", "Apple 76-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M2 Ultra","M-Series","Apple 76-Core Integrated","2TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.15,22);

        add(L, id++, "Dell Precision 7780 (128GB)",
            "Dell Precision top config with 128GB RAM.",
            649990, 4.7, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX 5000 Ada 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX 5000 Ada","4TB",
            "4K",60,2,2,true,true,"Good","Aluminum",2.8,7);

        add(L, id++, "HP ZBook Fury 17 G10 (128GB)",
            "HP workstation with maximum memory config.",
            699990, 4.6, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX A5000 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX A5000","4TB",
            "4K",60,3,2,true,true,"Good","Aluminum",3.0,6);

        add(L, id++, "ASUS ProArt Studiobook 16 OLED Max",
            "ASUS Pro workstation with RTX 5000 Ada.",
            699990, 4.7, IMG_WORK, 128, "Intel Core i9-13980HX", "NVIDIA RTX 5000 Ada 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 5000 Ada","2TB",
            "4K",60,2,2,true,true,"Premium","Aluminum",2.4,8);

        add(L, id++, "Lenovo ThinkPad P16 Gen 2 (128GB)",
            "ThinkPad P16 with max RAM for simulation.",
            749990, 4.7, IMG_WORK, 128, "Intel Core i9-13980HX", "NVIDIA RTX A5000 16GB",
            "15-16","IPS","Intel","i9","13th Gen","RTX A5000","4TB",
            "4K",60,2,2,true,true,"Good","Magnesium",2.8,7);

        add(L, id++, "Dell Precision 7780 Ultimate",
            "Dell ultimate workstation configuration.",
            799990, 4.7, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX 5000 Ada 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX 5000 Ada","4TB",
            "4K",60,3,2,true,true,"Good","Aluminum",2.8,7);

        add(L, id++, "HP ZBook Fury 17 G10 Ultimate",
            "HP's maximum-spec workstation flagship.",
            829990, 4.6, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX A5000 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX A5000","4TB",
            "4K",60,3,2,true,true,"Good","Aluminum",3.0,6);

        add(L, id++, "Apple MacBook Pro 16 M3 Max (96GB/4TB)",
            "Studio-grade Apple config for film and VFX.",
            849900, 4.9, IMG_MAC, 96, "Apple M3 Max 16-Core", "Apple 40-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M3 Max","M-Series","Apple 40-Core Integrated","4TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.14,22);

        add(L, id++, "ASUS ProArt Studiobook Pro 16 Enterprise",
            "ASUS enterprise-grade creator workstation.",
            899990, 4.7, IMG_WORK, 128, "Intel Core i9-13980HX", "NVIDIA RTX 5000 Ada 16GB",
            "15-16","OLED/Mini-LED","Intel","i9","13th Gen","RTX 5000 Ada","4TB",
            "4K",60,2,2,true,true,"Premium","Aluminum",2.4,8);

        add(L, id++, "Dell Precision 7780 Enterprise",
            "Dell enterprise workstation for data centers.",
            899990, 4.7, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX 5000 Ada 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX 5000 Ada","8TB",
            "4K",60,3,2,true,true,"Good","Aluminum",2.8,7);

        add(L, id++, "HP ZBook Fury 17 G10 Enterprise",
            "HP enterprise workstation flagship.",
            949990, 4.6, IMG_WORK, 128, "Intel Core i9-13950HX", "NVIDIA RTX A5000 16GB",
            "17+","IPS","Intel","i9","13th Gen","RTX A5000","8TB",
            "4K",60,3,2,true,true,"Good","Aluminum",3.0,6);

        add(L, id++, "Apple MacBook Pro 16 M3 Max (128GB/8TB)",
            "Ultimate Apple configuration for studios and enterprises.",
            999900, 5.0, IMG_MAC, 128, "Apple M3 Max 16-Core", "Apple 40-Core GPU",
            "15-16","OLED/Mini-LED","Apple","M3 Max","M-Series","Apple 40-Core Integrated","8TB",
            "QHD",120,0,3,true,true,"Premium","Aluminum",2.14,22);

        return L;
    }

    // ==========================================================================================
    //  AUTO-ASSIGN BENCHMARKS from CPU tier/generation
    // ==========================================================================================
    private void assignBenchmarks(List<Laptop> laptops) {
        for (Laptop l : laptops) {
            String tier = l.getCpuTier() != null ? l.getCpuTier().toLowerCase() : "";
            String gen = l.getCpuGeneration() != null ? l.getCpuGeneration().toLowerCase() : "";
            String chip = l.getChipsetFamily() != null ? l.getChipsetFamily().toLowerCase() : "";

            int single = 1200, multi = 5000, cine = 6000;

            if (chip.equals("apple")) {
                if (tier.contains("m3 max"))      { single = 3100; multi = 21000; cine = 24000; }
                else if (tier.contains("m3 pro")) { single = 3000; multi = 15000; cine = 17000; }
                else if (tier.contains("m3"))     { single = 3000; multi = 12000; cine = 13000; }
                else if (tier.contains("m2"))     { single = 2600; multi = 10000; cine = 11000; }
                else                              { single = 2400; multi = 8000;  cine = 9000;  }
            } else if (chip.equals("intel")) {
                if (tier.contains("i9"))          { single = 2800; multi = 18000; cine = 20000; }
                else if (tier.contains("i7"))     { single = 2500; multi = 14000; cine = 15000; }
                else if (tier.contains("i5"))     { single = 2100; multi = 10000; cine = 10000; }
                else                              { single = 1700; multi = 6000;  cine = 6500;  }
                // Generation bump
                if (gen.contains("14")) { single += 200; multi += 1500; cine += 2000; }
                else if (gen.contains("13")) { single += 100; multi += 1000; cine += 1000; }
            } else if (chip.equals("amd")) {
                if (tier.contains("ryzen 9"))     { single = 2700; multi = 17000; cine = 19000; }
                else if (tier.contains("ryzen 7")){ single = 2300; multi = 13000; cine = 14000; }
                else if (tier.contains("ryzen 5")){ single = 2000; multi = 9500;  cine = 9500;  }
                else                              { single = 1500; multi = 5500;  cine = 5500;  }
                if (gen.contains("zen 4")) { single += 200; multi += 1500; cine += 1500; }
                else if (gen.contains("zen 3+")) { single += 100; multi += 800; cine += 800; }
            }

            l.setGeekbenchSingle(single);
            l.setGeekbenchMulti(multi);
            l.setCinebenchScore(cine);
        }
    }

    private Laptop copy(Laptop s) {
        Laptop l = new Laptop();
        l.setId(s.getId()); l.setTitle(s.getTitle()); l.setDescription(s.getDescription());
        l.setPrice(s.getPrice()); l.setRating(s.getRating()); l.setThumbnail(s.getThumbnail());
        l.setRamGB(s.getRamGB()); l.setCpu(s.getCpu()); l.setGpu(s.getGpu());
        l.setDisplaySize(s.getDisplaySize()); l.setDisplayType(s.getDisplayType()); l.setChipsetFamily(s.getChipsetFamily());
        l.setCpuTier(s.getCpuTier()); l.setCpuGeneration(s.getCpuGeneration()); l.setGpuModel(s.getGpuModel());
        l.setStorageCapacity(s.getStorageCapacity()); l.setResolution(s.getResolution()); l.setRefreshRate(s.getRefreshRate());
        l.setUsbACount(s.getUsbACount()); l.setUsbCCount(s.getUsbCCount());
        l.setHdmiPort(s.isHdmiPort()); l.setSdCardSlot(s.isSdCardSlot());
        l.setSpeakerQuality(s.getSpeakerQuality()); l.setBuildMaterial(s.getBuildMaterial());
        l.setWeight(s.getWeight()); l.setBatteryLife(s.getBatteryLife());
        l.setGeekbenchSingle(s.getGeekbenchSingle()); l.setGeekbenchMulti(s.getGeekbenchMulti());
        l.setCinebenchScore(s.getCinebenchScore());
        l.setPerfScore(s.getPerfScore()); l.setDisplayScore(s.getDisplayScore());
        l.setPortabilityScore(s.getPortabilityScore()); l.setBuildScore(s.getBuildScore());
        l.setBatteryScore(s.getBatteryScore()); l.setWhyThisLaptop(s.getWhyThisLaptop());
        return l;
    }
}
