package pro.dprof.dorprofzhelzszd.utils;

public final class Constants {

    public final static String INTENT_TAG_ASSET_NAME = "asset_name";
    public final static String INTENT_TAG_ACTIVITY_TITLE = "activity_title";

    //Preferences
    public final static String PREF_APP_STATE = "pref_app_state";
    public final static String PREF_NOTE = "pref_note";
    public final static String PREF_ABOUT_ORG = "pref_about_org";
    public final static String PREF_STAFF = "pref_staff";

    //DocumentsAdapter
    public final static String SECTION_PLENARY = "Постановление Пленумов, Президиумов";
    public final static String SECTION_ORGANIZATIONAL = "Отдел организационной и кадровой работы";
    public final static String SECTION_ECONOMIC = "Отдел социально-экономической защиты";
    public final static String SECTION_SOCIAL = "Отдел социальной сферы";
    public final static String SECTION_LEGAL = "Правовая инспекция труда";
    public final static String SECTION_TECHNICAL = "Техническая инспекция труда";

    public final static String[] SECTIONS = {
            SECTION_PLENARY,
            SECTION_ORGANIZATIONAL,
            SECTION_ECONOMIC,
            SECTION_SOCIAL,
            SECTION_LEGAL,
            SECTION_TECHNICAL
    };

    public final static String[] DOC_SECTION_PLENARY_ITEM_TITLES = {
            "Постановление Президиума О проведении собеседования с освобожденными выборными работниками",
            "Постановление № 13 от 27.07.2017 г. «О соблюдении норм трудового законодательства и выполнение коллективного договора в структурных подразделениях Западно-Сибирской дирекции инфраструктуры Омского региона",
            "ПОСТАНОВЛЕНИЕ IV Пленума - Об информационной работе в организациях Профсоюза в границах железной дороги",
            "Об отчетах в ППО ОАО РЖД РОСПРОФЖЕЛ на Западно- Сибирской железной дороге",
            "Об отчетах в Дорожной территориальной организации РОСПРОФЖЕЛ на Западно- Сибирской железной дороге",
            "Об обеспечении работников Западно-Сибирской железной дороги, дирекций, ДЗО, спецодеждой, спецобувью и другими средствами индивидуальной защиты",
            "О работе первичной профсоюзной организации ОАО Новосибирский стрелочный завод по вопросам социально-экономической защиты работников"
    };
    public final static String[] DOC_SECTION_PLENARY_ACTIVITY_TITLES = {
            "Постановление № 15",
            "Постановление № 13",
            "Постановление № 4.1",
            "Постановление № 5",
            "Постановление № 5",
            "Постановление № 13.1",
            "Постановление № 13.1",
    };
    public final static String[] DOC_SECTION_PLENARY_ASSETS_NAMES = {
            "plenary_about_interview.pdf",
            "plenary_about_compliance.pdf",
            "plenary_about_information_work.pdf",
            "plenary_about_reports_ppo.pdf",
            "plenary_about_reports_rosprofzhel.pdf",
            "plenary_about_provision.pdf",
            "plenary_about_work.pdf",
    };

    public final static String[] DOC_SECTION_ORGANIZATIONAL_ITEM_TITLES = {
            "Концепция кадровой политики Роспрофжел",
            "Структура Дорпрофжел",
            "Список членов ППО ОАО РЖД на Западно- Сибирской железной дороге",
            "Список членов Дорожного комитета Профсоюза",
            "Состав Президиума Дорпрофжел",
            "Регламент работы выборных органов"
    };
    public final static String[] DOC_SECTION_ORGANIZATIONAL_ACTIVITY_TITLES = {
            "Кадровая политика",
            "Структура Дорпрофжел",
            "РОСПРОФЖЕЛ на ЗСЖД",
            "Дорпрофжел на ЗСЖД",
            "Президиум Дорпрофжел",
            "Регламент"
    };
    public final static String[] DOC_SECTION_ORGANIZATIONAL_ASSETS_NAMES = {
            "organizational_concept.pdf",
            "organizational_structure.pdf",
            "organizational_members_rosprofzhel.pdf",
            "organizational_members_dorprofzhel.pdf",
            "organizational_presidium_dorprofzhel.pdf",
            "organizational_regulations.pdf"
    };

    public final static String[] DOC_SECTION_ECONOMIC_ITEM_TITLES = {
            "Устав РОСПРОФЖЕЛ",
            "Коллективный договор ОАО \"РЖД\" на 2017-2019 г",
            "Колллективный договоро АО ВРК-3 на 2017-2019 гг.",
            "Коллективный договор ООО ТМХ-Сервис на 2017-2019 гг.",
            "Коллективный договор ООО СТМ-Сервис на 2017-2019 гг.",
            "Коллективный договор ОА ВРК-1 на 2017-2019 гг.",
            "Коллективный договор АО Первая Грузовая Компания на 2017-2019 гг.",
            "Коллективный договор АО ВРК-2 на 2017-2019 гг."
    };
    public final static String[] DOC_SECTION_ECONOMIC_ACTIVITY_TITLES = {
            "Устав РОСПРОФЖЕЛ",
            "Договор ОАО \"РЖД\"",
            "Договор АО ВРК-3",
            "Договор ООО ТМХ-Сервис",
            "Договор ООО СТМ-Сервис",
            "Договор ОА ВРК-1",
            "Договор АО ПГК",
            "Договор АО ВРК-2"
    };
    public final static String[] DOC_SECTION_ECONOMIC_ASSETS_NAMES = {
            "economic_statute.pdf",
            "economic_contract_rzd.pdf",
            "economic_contract_vrk3.pdf",
            "economic_contract_tmh.pdf",
            "economic_contract_stm.pdf",
            "economic_contract_vrk1.pdf",
            "economic_contract_pgk.pdf",
            "economic_contract_vrk2.pdf"
    };

    public final static String[] DOC_SECTION_SOCIAL_ITEM_TITLES = {
            "ВТБ-24 Страхование - Памятка Управляй здоровьем",
            "Краткая информация о работе Дорожной организации Красный Крест"
    };
    public final static String[] DOC_SECTION_SOCIAL_ACTIVITY_TITLES = {
            "Управляй здоровьем",
            "Красный крест"
    };
    public final static String[] DOC_SECTION_SOCIAL_ASSETS_NAMES = {
            "social_vtb24.pdf",
            "social_red_cross.pdf"
    };

    public final static String[] DOC_SECTION_LEGAL_ITEM_TITLES = {
            "УВОЛЬНЕНИЕ ПОД ДАВЛЕНИЕМ И ОШИБКИ КОМПАНИЙ. АНАЛИЗ СУДЕБНОЙ ПРАКТИКИ",
            "Немного истории_ ВЕЛИКИЕ РАБОТОДАТЕЛИ МИРA",
            "Консультация о порядке расторжения срочного трудового договора с беременной женщиной",
            "Выплата больничных после сокращения работника. Консультация специалиста",
            "Список правовых инспекторов труда Роспрофжел на ЗапСиб ЖД",
            "Правовой инструктаж в вопросах и ответах_ненормированный рабочий день",
            "Порядок выдачи расчетных листков работнику (по материалам Консультант+)",
            "Положение о молодом специалисте ОАО РЖД (распоряжение ОАО РЖД от 18.07.2017 № 1397р",
            "Оплата работы в выходные и праздничные дни в период командировки работнику, получающему оклад",
            "О ВНЕСЕНИИ ИЗМЕНЕНИЙ В ТРУДОВОЙ КОДЕКС РОССИЙСКОЙ ФЕДЕРАЦИИ",
            "Консультация_В каких случаях и как оплачивается время отсутствия на работе в случае вызова работника по повестке в суд и другие органы",
            "КОГДА МОЖНО ПРЕДУПРЕДИТЬ РАБОТНИКА О СОКРАЩЕНИИ ПО ПОЧТЕ (по материалам Консультан+)",
            "ДЕКРЕТНАЯ МАТРЕШКА_ КАК НЕ ЗАПУТАТЬСЯ_По материалам Консультант+",
            "Противоправное бездействие работодателя- наказуемо!",
            "Консультация специалиста_СПОРЫ О ДАТАХ УВОЛЬНЕНИЯ (по материалам журнала Трудовые споры)",
            "Консультация_Сотрудник не был на работе 1,5 часа до обеда и 3 часа после - можно ли его уволить за прогул",
            "Трудовой кодекс РФ"
    };
    public final static String[] DOC_SECTION_LEGAL_ACTIVITY_TITLES = {
            "Увольнение",
            "Немного истории",
            "Расторжение",
            "Выплата больничных",
            "Список инспекторов",
            "Рабочий день",
            "Расчётные листы",
            "Молодой специалист",
            "Выходные",
            "Трудовой кодекс",
            "Отсутствие на работе",
            "Сокращение",
            "Декретная матрёшка",
            "Бездействие",
            "Дата увольнения",
            "Прогул",
            "Трудовой кодекс РФ"

    };
    public final static String[] DOC_SECTION_LEGAL_ASSETS_NAMES = {
            "legal_firing.pdf",
            "legal_history.pdf",
            "legal_termination.pdf",
            "legal_sick_pay.pdf",
            "legal_list.pdf",
            "legal_working_day.pdf",
            "legal_sheets.pdf",
            "legal_young.pdf",
            "legal_weekends.pdf",
            "legal_changes.pdf",
            "legal_absence.pdf",
            "legal_mail.pdf",
            "legal_decree.pdf",
            "legal_inaction.pdf",
            "legal_about_date.pdf",
            "legal_absenteeism.pdf",
            "legal_code.pdf"
    };

    public final static String[] DOC_SECTION_TECHNICAL_ITEM_TITLES = {
            "Презентация по СОУТ",
            "Спецоценка вопросы и ответы",
            "Телефоны технических инспекторов Дорпрофжел",
            "Что нужно знать при СОУТ",
            "Обращение начальника Западно-Сибирской железной дороги А.А.Регера и председателя Дорпрофжел Н.В. Шашкова к работникам дороги.",
            "О повышенной оплате труда работников, занятых на работах с вредными и (или) опасными условиями труда",
            "Список технических инспекторов"
    };
    public final static String[] DOC_SECTION_TECHNICAL_ACTIVITY_TITLES = {
            "Презентация по СОУТ",
            "Спецоценка",
            "Телефоны ТИ",
            "СОУТ",
            "Обращение",
            "О повышенной оплате",
            "Список ТИ"

    };
    public final static String[] DOC_SECTION_TECHNICAL_ASSETS_NAMES = {
            "technical_presentation.pdf",
            "technical_evaluation.pdf",
            "technical_phones.pdf",
            "technical_sout.pdf",
            "technical_appeal.pdf",
            "technical_about raising.pdf",
            "technical_list.pdf"
    };
}
