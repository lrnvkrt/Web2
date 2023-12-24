package com.example.Web2.init;

import com.example.Web2.dtos.*;
import com.example.Web2.models.Model;
import com.example.Web2.models.Offer;
import com.example.Web2.models.UserRole;
import com.example.Web2.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandServiceImpl brandService;

    private final ModelServiceImpl modelService;

    private final UserRoleServiceImpl userRoleService;

    private final UserServiceImpl userService;

    private final OfferServiceImpl offerService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(BrandServiceImpl brandService, ModelServiceImpl modelService, UserRoleServiceImpl userRoleService, UserServiceImpl userService, OfferServiceImpl offerService, PasswordEncoder passwordEncoder) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.offerService = offerService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        newSeedData();
    }

    private void oldSeedData() {
        BrandDto brandDto = new BrandDto("Volvo");
        BrandDto savedBrand = brandService.addBrand(brandDto);
        BrandDto brandDto1 = new BrandDto("BMW");
        BrandDto savedBrand1 = brandService.addBrand(brandDto1);
        savedBrand1.setName("Tesla");
        brandService.updateBrand(savedBrand1);

        ModelDto modelDto = new ModelDto("X16", Model.Category.Car, null, 2012, 2018, savedBrand.getName());
        ModelDto savedModel = modelService.addModel(modelDto);
        ModelDto modelDto1 = new ModelDto("VRUM-VRUM", Model.Category.Car, null, 1980, 2080, savedBrand.getName());
        ModelDto savedModel1 = modelService.addModel(modelDto1);
        savedModel.setEndYear(2020);
        savedModel.setImageUrl("фотка_классной_машины.png");
        modelService.updateModel(savedModel);
        System.out.println(savedModel);

        UserRoleDto userRoleDto = new UserRoleDto(UserRole.Role.User);
        UserRoleDto savedRoleUser = userRoleService.addUserRole(userRoleDto);
        UserRoleDto userRoleDto1 = new UserRoleDto(UserRole.Role.Admin);
        UserRoleDto savedRoleAdmin = userRoleService.addUserRole(userRoleDto1);
        System.out.println("Искомая роль!!! " + userRoleService.findById(savedRoleUser.getId()));

        UserDto userDto = new UserDto("touareg_3000", "Арсений", "Дубровский", "сенечка.jpg", savedRoleAdmin);
        UserDto savedUser = userService.addUser(userDto);
        UserDto userDto1 = new UserDto("not_bad_seller", "Александра", "Тесла", "саня.jpeg", savedRoleUser);
        UserDto savedUser1 = userService.addUser(userDto1);
        userService.updateUser(savedUser1);
        userService.changeActivation(savedUser1.getUsername());

        OfferDto offerDto = new OfferDto("Самая лучшая машина!", Offer.Engine.DIESEL, "лучшая_машина.jpg", 100, new BigDecimal(1_000_000), Offer.Transmission.AUTOMATIC, 2022, savedModel.getId(), savedUser.getUsername());
        OfferDto savedOffer = offerService.addOffer(offerDto);
        OfferDto offerDto1 = new OfferDto("Тоже неплохая машина!", Offer.Engine.ELECTRIC, "чуть_хуже_машина.jpg", 1000, new BigDecimal(15_000_000), Offer.Transmission.AUTOMATIC, 2023, savedModel1.getId(), savedUser1.getUsername());
        OfferDto savedOffer1 = offerService.addOffer(offerDto1);
        savedOffer.setMileage(200);
        offerService.updateOffer(savedOffer);
        System.out.println(modelService.findAllModels());
        System.out.println(modelService.findModelsByBrandName(savedBrand.getName()));
        System.out.println(offerService.findAllOffersByActiveUsersAndModel(savedModel.getId()));
    }

    private void newSeedData() {
        // Создание брендов
        BrandDto brandDto1 = new BrandDto("Toyota");
        BrandDto savedBrand1 = brandService.addBrand(brandDto1);

        BrandDto brandDto2 = new BrandDto("Honda");
        BrandDto savedBrand2 = brandService.addBrand(brandDto2);

        BrandDto brandDto3 = new BrandDto("Ford");
        BrandDto savedBrand3 = brandService.addBrand(brandDto3);

        BrandDto brandDto4 = new BrandDto("Chevrolet");
        BrandDto savedBrand4 = brandService.addBrand(brandDto4);

        BrandDto brandDto5 = new BrandDto("Nissan");
        BrandDto savedBrand5 = brandService.addBrand(brandDto5);

        // Создание моделей машин для каждого бренда
        ModelDto modelDto1 = new ModelDto("Camry", Model.Category.Car, "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg", 2015, 2022, savedBrand1.getName());
        ModelDto savedModel1 = modelService.addModel(modelDto1);

        ModelDto modelDto2 = new ModelDto("Corolla", Model.Category.Car, "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg", 2012, 2020, savedBrand1.getName());
        ModelDto savedModel2 = modelService.addModel(modelDto2);

        ModelDto modelDto3 = new ModelDto("Civic", Model.Category.Car, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 2010, 2023, savedBrand2.getName());
        ModelDto savedModel3 = modelService.addModel(modelDto3);

        ModelDto modelDto4 = new ModelDto("Accord", Model.Category.Car, "https://kuznitsaspb.ru/wp-content/uploads/7/2/c/72c8ff20016e38a80364fe66b330a40e.jpeg", 2018, 2022, savedBrand2.getName());
        ModelDto savedModel4 = modelService.addModel(modelDto4);

        ModelDto modelDto5 = new ModelDto("Fusion", Model.Category.Car, "http://wp1.carwallpapers.ru/ford/fusion/2005/Ford-Fusion-2005-1920x1080-008.jpg", 2016, 2021, savedBrand3.getName());
        ModelDto savedModel5 = modelService.addModel(modelDto5);

        ModelDto modelDto6 = new ModelDto("Escape", Model.Category.Car, "https://627400.ru/wp-content/uploads/7/0/4/704cad530f28e48621d9117f1bfeff39.jpeg", 2019, 2023, savedBrand3.getName());
        ModelDto savedModel6 = modelService.addModel(modelDto6);

        ModelDto modelDto7 = new ModelDto("Malibu", Model.Category.Car, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 2017, 2022, savedBrand4.getName());
        ModelDto savedModel7 = modelService.addModel(modelDto7);

        ModelDto modelDto8 = new ModelDto("Cruze", Model.Category.Car, "https://img.favcars.com/chevrolet/cruze/chevrolet_cruze_2012_pictures_8_1600x1200.jpg", 2014, 2019, savedBrand4.getName());
        ModelDto savedModel8 = modelService.addModel(modelDto8);

        ModelDto modelDto9 = new ModelDto("Altima", Model.Category.Car, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 2013, 2018, savedBrand5.getName());
        ModelDto savedModel9 = modelService.addModel(modelDto9);

        ModelDto modelDto10 = new ModelDto("Maxima", Model.Category.Car, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 2016, 2020, savedBrand5.getName());
        ModelDto savedModel10 = modelService.addModel(modelDto10);

        UserRoleDto userRoleDto = new UserRoleDto(UserRole.Role.User);
        UserRoleDto savedRoleUser = userRoleService.addUserRole(userRoleDto);
        UserRoleDto userRoleDto1 = new UserRoleDto(UserRole.Role.Admin);
        UserRoleDto savedRoleAdmin = userRoleService.addUserRole(userRoleDto1);

        // Создание пользователей с ролью User
        UserRegistrationDto userDto1 = new UserRegistrationDto("bullet_1",  "password123", "password123", "Олег", "Алегов", "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg");
        userService.register(userDto1);

        UserRegistrationDto userDto2 = new UserRegistrationDto("auto_wired", "securepass", "securepass", "Иван", "Простаков", "https://627400.ru/wp-content/uploads/7/0/4/704cad530f28e48621d9117f1bfeff39.jpeg");
        userService.register(userDto2);

        UserRegistrationDto userDto3 = new UserRegistrationDto("senya_car", "userpass", "userpass", "Арсений", "Дубов", null);
        userService.register(userDto3);
        userService.changeRole("senya_car", UserRole.Role.Admin);

        UserRegistrationDto userDto4 = new UserRegistrationDto("good_looking_driver", "pass123", "pass123", "Евплампий", "Дуров", "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg");
        userService.register(userDto4);

        UserRegistrationDto userDto5 = new UserRegistrationDto("speedy", "user12345", "user12345", "Самуил", "Деревьев", "https://www.meme-arsenal.com/memes/26864047321e091cd259269a3b5d7c1a.jpg");
        userService.register(userDto5);

        UserRegistrationDto userDto6 = new UserRegistrationDto("user_car_sale", "password6", "password6", "Антон", "Васильев", "https://gas-kvas.com/grafic/uploads/posts/2023-10/1696442556_gas-kvas-com-p-kartinki-sportkar-4.jpg");
        userService.register(userDto6);

        UserRegistrationDto userDto7 = new UserRegistrationDto("auto_man", "secure7", "secure7", "Василий", "Антонов", "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg");
        userService.register(userDto7);

        UserRegistrationDto userDto8 = new UserRegistrationDto("lightning_3000", "pass8", "pass8", "Ирина", "Гроза", "https://gas-kvas.com/grafic/uploads/posts/2023-10/1696442556_gas-kvas-com-p-kartinki-sportkar-4.jpg");
        userService.register(userDto8);

        UserRegistrationDto userDto9 = new UserRegistrationDto("racer_9", "user9pass", "user9pass", "Валерий", "Булкин", "https://www.rusgvozdi.ru/upload/iblock/d22/d2217bcbf63c24ad94783d2f499136e9.jpeg");
        userService.register(userDto9);

        UserRegistrationDto userDto10 = new UserRegistrationDto("adventurer_00", "passuser10", "passuser10", "Илья", "Березов", null);
        userService.register(userDto10);

        // Создание объявлений
        OfferDto offerDto1 = new OfferDto("Прекрасный автомобиль по скидке!", Offer.Engine.GASOLINE, "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg", 5000, new BigDecimal(200000), Offer.Transmission.AUTOMATIC, 2021, savedModel1.getId(), "bullet_1");
        OfferDto savedOffer1 = offerService.addOffer(offerDto1);

        OfferDto offerDto2 = new OfferDto("Надежный и экономичный автомобиль.", Offer.Engine.DIESEL, "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg", 3000, new BigDecimal(1500000), Offer.Transmission.MANUAL, 2022, savedModel2.getId(), "auto_wired");
        OfferDto savedOffer2 = offerService.addOffer(offerDto2);

        OfferDto offerDto3 = new OfferDto("Семейный седан с низким пробегом.", Offer.Engine.HYBRID, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 7000, new BigDecimal(250000), Offer.Transmission.AUTOMATIC, 2020, savedModel3.getId(), "good_looking_driver");
        OfferDto savedOffer3 = offerService.addOffer(offerDto3);

        OfferDto offerDto4 = new OfferDto("Спортивный и стильный автомобиль!", Offer.Engine.GASOLINE, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 8000, new BigDecimal(1800000), Offer.Transmission.MANUAL, 2023, savedModel4.getId(), "speedy");
        OfferDto savedOffer4 = offerService.addOffer(offerDto4);

        OfferDto offerDto5 = new OfferDto("Экономичный и компактный автомобиль!", Offer.Engine.HYBRID, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 4000, new BigDecimal(2200000), Offer.Transmission.AUTOMATIC, 2021, savedModel5.getId(), "good_looking_driver");
        OfferDto savedOffer5 = offerService.addOffer(offerDto5);

        OfferDto offerDto6 = new OfferDto("Просторный внедорожник, идеальный для семейных поездок.", Offer.Engine.GASOLINE, "https://maslocar.com/wp-content/uploads/2020/07/novo-corolla-2015-1-1536x961.jpg", 6000, new BigDecimal(3000000), Offer.Transmission.AUTOMATIC, 2022, savedModel6.getId(), "auto_wired");
        OfferDto savedOffer6 = offerService.addOffer(offerDto6);

        OfferDto offerDto7 = new OfferDto("Комфортный и надёжный седан!", Offer.Engine.DIESEL, "https://627400.ru/wp-content/uploads/7/0/4/704cad530f28e48621d9117f1bfeff39.jpeg", 4500, new BigDecimal(1700000), Offer.Transmission.AUTOMATIC, 2020, savedModel7.getId(), "bullet_1");
        OfferDto savedOffer7 = offerService.addOffer(offerDto7);

        OfferDto offerDto8 = new OfferDto("Роскошный автомобиль!", Offer.Engine.HYBRID, "https://627400.ru/wp-content/uploads/7/0/4/704cad530f28e48621d9117f1bfeff39.jpeg", 7500, new BigDecimal(280000), Offer.Transmission.AUTOMATIC, 2021, savedModel8.getId(), "user_car_sale");
        OfferDto savedOffer8 = offerService.addOffer(offerDto8);

        OfferDto offerDto9 = new OfferDto("Отличный автомобиль для городской езды!", Offer.Engine.GASOLINE, "https://img.favcars.com/chevrolet/cruze/chevrolet_cruze_2012_pictures_8_1600x1200.jpg", 3500, new BigDecimal(1900000), Offer.Transmission.MANUAL, 2022, savedModel9.getId(), "racer_9");
        OfferDto savedOffer9 = offerService.addOffer(offerDto9);

        OfferDto offerDto10 = new OfferDto("Электромобиль, экологически чистый.", Offer.Engine.ELECTRIC, "http://wp1.carwallpapers.ru/ford/fusion/2005/Ford-Fusion-2005-1920x1080-008.jpg", 2000, new BigDecimal(350000), Offer.Transmission.AUTOMATIC, 2023, savedModel10.getId(), "auto_man");
        OfferDto savedOffer10 = offerService.addOffer(offerDto10);

        OfferDto offerDto11 = new OfferDto("Премиум внедорожник с высокотехнологичными возможностями.", Offer.Engine.GASOLINE, "https://img.favcars.com/chevrolet/cruze/chevrolet_cruze_2012_pictures_8_1600x1200.jpg", 900000, new BigDecimal(40000), Offer.Transmission.AUTOMATIC, 2022, savedModel1.getId(), "lightning_3000");
        OfferDto savedOffer11 = offerService.addOffer(offerDto11);

        OfferDto offerDto12 = new OfferDto("Прекрасный гибрид по скидке!", Offer.Engine.HYBRID, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 5500, new BigDecimal(240000), Offer.Transmission.AUTOMATIC, 2021, savedModel2.getId(), "auto_wired");
        OfferDto savedOffer12 = offerService.addOffer(offerDto12);

        OfferDto offerDto13 = new OfferDto("Стильный кабриолет, идеальный для летних поездок", Offer.Engine.GASOLINE, "http://wp1.carwallpapers.ru/ford/fusion/2005/Ford-Fusion-2005-1920x1080-008.jpg", 7000, new BigDecimal(280000), Offer.Transmission.MANUAL, 2020, savedModel3.getId(), "racer_9");
        OfferDto savedOffer13 = offerService.addOffer(offerDto13);

        OfferDto offerDto14 = new OfferDto("Прекрасный вариант для городской жизни!", Offer.Engine.GASOLINE, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 6000, new BigDecimal(2200000), Offer.Transmission.AUTOMATIC, 2023, savedModel4.getId(), "adventurer_00");
        OfferDto savedOffer14 = offerService.addOffer(offerDto14);

        OfferDto offerDto15 = new OfferDto("Автомобиль для комфортной езды по бездорожью!!", Offer.Engine.HYBRID, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 4000, new BigDecimal(1900000), Offer.Transmission.AUTOMATIC, 2021, savedModel5.getId(), "speedy");
        OfferDto savedOffer15 = offerService.addOffer(offerDto15);

        OfferDto offerDto16 = new OfferDto("Идеальный варинт для семейных поездок!", Offer.Engine.GASOLINE, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 8000, new BigDecimal(320000), Offer.Transmission.AUTOMATIC, 2022, savedModel6.getId(), "auto_man");
        OfferDto savedOffer16 = offerService.addOffer(offerDto16);

        OfferDto offerDto17 = new OfferDto("Спортивный автомобиль с низким пробегом!", Offer.Engine.GASOLINE, "https://gas-kvas.com/grafic/uploads/posts/2023-10/1696442556_gas-kvas-com-p-kartinki-sportkar-4.jpg", 9000, new BigDecimal(2500000), Offer.Transmission.MANUAL, 2020, savedModel7.getId(), "lightning_3000");
        OfferDto savedOffer17 = offerService.addOffer(offerDto17);

        OfferDto offerDto18 = new OfferDto("Продвинутый гибрид!", Offer.Engine.HYBRID, "https://www.simplycars.ru/cache/front/photoalbums/22/43774/1580x1140.jpg", 7500, new BigDecimal(290000), Offer.Transmission.AUTOMATIC, 2021, savedModel8.getId(), "senya_car");
        OfferDto savedOffer18 = offerService.addOffer(offerDto18);

        OfferDto offerDto19 = new OfferDto("Стильный кабриолет!", Offer.Engine.GASOLINE, "https://kuznitsaspb.ru/wp-content/uploads/5/0/2/50283898d7436daaaf3c965cce0b151c.jpeg", 3500, new BigDecimal(2000000), Offer.Transmission.MANUAL, 2022, savedModel9.getId(), "user_car_sale");
        OfferDto savedOffer19 = offerService.addOffer(offerDto19);

        OfferDto offerDto20 = new OfferDto("Электромобиль по скидке!", Offer.Engine.ELECTRIC, "http://wp1.carwallpapers.ru/ford/fusion/2005/Ford-Fusion-2005-1920x1080-008.jpg", 2000, new BigDecimal(3700000), Offer.Transmission.AUTOMATIC, 2023, savedModel10.getId(), "senya_car");
        OfferDto savedOffer20 = offerService.addOffer(offerDto20);
    }
}
