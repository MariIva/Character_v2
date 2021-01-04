package ru.mora.character_v2;

import android.net.Uri;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

// фрагмент создания персонажа
public class CharFragment extends Fragment {
    TextInputLayout m_et_name;
    TextInputLayout m_et_age;
    TextInputLayout m_et_sex;
    TextInputLayout m_et_race;
    TextInputLayout m_et_str;
    TextInputLayout m_et_dex;
    TextInputLayout m_et_con;
    TextInputLayout m_et_intl;
    TextInputLayout m_et_wis;
    TextInputLayout m_et_charm;
    ImageView imageView;
    CoordinatorLayout coordinatorLayout;
    OnCharFragmentListener listener;


    // путь к картинке
    Uri selectedImageURI = null;

    // входные данные при создании объекта фрагмента
    private static final String ARG_VALUES = "ARG_VALUES";
    String strings[] = null;

    // интервейс для реализации в активности
    public interface OnCharFragmentListener {
        void onImageClick();
        void getCharacter(Character character);
    }

    // создание фрагмента с входными данными
    public static CharFragment newInstance(String[] strings) {
        CharFragment fragment = new CharFragment();
        // сохрание входных данных
        Bundle args = new Bundle();
        args.putStringArray(ARG_VALUES, strings);
        fragment.setArguments(args);
        return fragment;
    }

    // создание фрагмента
    public CharFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // установка меню в активность
        setHasOptionsMenu(true);
        // получение входных данных
        strings = getArguments().getStringArray(ARG_VALUES);
    }

    // получение значений введенных пользователем в поля ввода
    public String[] getStrings(){
        String[] strings = new String[11];
        if (selectedImageURI !=null){
            strings[0] = selectedImageURI.toString();
        }
        else{
            strings[0] ="";
        }
        strings[1] = m_et_name.getEditText().getText().toString();
        strings[2] = m_et_age.getEditText().getText().toString();
        strings[3] = m_et_sex.getEditText().getText().toString();
        strings[4] = m_et_race.getEditText().getText().toString();
        strings[5] = m_et_str.getEditText().getText().toString();
        strings[6] = m_et_dex.getEditText().getText().toString();
        strings[7] = m_et_con.getEditText().getText().toString();
        strings[8] = m_et_intl.getEditText().getText().toString();
        strings[9] = m_et_wis.getEditText().getText().toString();
        strings[10] = m_et_charm.getEditText().getText().toString();
        return strings;
    }

    // установка значений в поля ввода
    public void setStrings(){
        if (strings[0].equals("")){
            imageView.setImageResource(R.drawable.char_icon);
        }
        else{
            imageView.setImageURI(Uri.parse(strings[0]));
        }
        m_et_name.getEditText().setText(strings[1]);
        m_et_age.getEditText().setText(strings[2]);
        m_et_sex.getEditText().setText(strings[3]);
        m_et_race.getEditText().setText(strings[4]);
        m_et_str.getEditText().setText(strings[5]);
        m_et_dex.getEditText().setText(strings[6]);
        m_et_con.getEditText().setText(strings[7]);
        m_et_intl.getEditText().setText(strings[8]);
        m_et_wis.getEditText().setText(strings[9]);
        m_et_charm.getEditText().setText(strings[10]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_char_v1, container, false);

        // получение методов интерфейса реализованных в активности
        listener = (OnCharFragmentListener) getActivity();

        m_et_name = (TextInputLayout) view.findViewById(R.id.m_et_name) ;
        m_et_age = (TextInputLayout) view.findViewById(R.id.m_et_age) ;
        m_et_sex = (TextInputLayout) view.findViewById(R.id.m_et_sex) ;
        m_et_race = (TextInputLayout) view.findViewById(R.id.m_et_race) ;
        m_et_str = (TextInputLayout) view.findViewById(R.id.m_et_str) ;
        m_et_dex = (TextInputLayout) view.findViewById(R.id.m_et_dex) ;
        m_et_con = (TextInputLayout) view.findViewById(R.id.m_et_con) ;
        m_et_intl = (TextInputLayout) view.findViewById(R.id.m_et_intl) ;
        m_et_wis = (TextInputLayout) view.findViewById(R.id.m_et_wis) ;
        m_et_charm = (TextInputLayout) view.findViewById(R.id.m_et_charm) ;
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.c_l);

        imageView = (ImageView) view.findViewById(R.id.image_char);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // запуск метода активности
                listener.onImageClick();
            }
        });

        // установка значений строк
        if (strings!=null) setStrings();

        return view;
    }

    // создание меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_char, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // кнопка сохранить
            case R.id.save_char:
                // создание персонажа
                // вызов метода из активности
                listener.getCharacter(createChar());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // установка картинки
    public void setImage(Uri uri){
        selectedImageURI = uri;
        imageView.setImageURI(selectedImageURI);
    }

    // создание персонажа
    private Character createChar(){
        Character character = null;
        String img = "";
        if (selectedImageURI !=null){
            img = selectedImageURI.toString();
        }

        // считываение введенных данных
        try {
            String name = m_et_name.getEditText().getText().toString();
            int age = Integer.parseInt(m_et_age.getEditText().getText().toString());
            String sex = m_et_sex.getEditText().getText().toString();
            String race = m_et_race.getEditText().getText().toString();
            int str = Integer.parseInt(m_et_str.getEditText().getText().toString());
            int dex = Integer.parseInt(m_et_dex.getEditText().getText().toString());
            int con = Integer.parseInt(m_et_con.getEditText().getText().toString());
            int intl = Integer.parseInt(m_et_intl.getEditText().getText().toString());
            int wis = Integer.parseInt(m_et_wis.getEditText().getText().toString());
            int charm = Integer.parseInt(m_et_charm.getEditText().getText().toString());

            // если не введены текстовые значения, то бросить исключение
            if (name.equals("")||sex.equals("")||race.equals("")){
                NullPointerException exception=new NullPointerException("Текст не введен");
                throw exception;
            }
            // создаем объект персонажа
            character = new Character(img, name, age, sex, race,
                    str, dex, con, intl, wis, charm);
            clearChar();

        }
        /* NumberFormatException - если пользователь не ввел ни одной цифры, то будет ошибка
        перевода строки "" в число
        NullPointerException -  брошеная в if ошибка
        */
        // обработчик исключений
        catch (NumberFormatException | NullPointerException e){
            //создаем Snackbar
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Введите все данные", Snackbar.LENGTH_INDEFINITE);
            // добавляем кнопку на Snackbar и описываем клик на нее
            snackbar.setAction("OK", new View.OnClickListener (){
                @Override
                public void onClick(View v) {
                    // при клике закрываем Snackbar
                    snackbar.dismiss();
                }
            });
            // показываем Snackbar
            snackbar.show();
        }

        return character;
    }

    // очистка полей ввода
    private void clearChar(){
        imageView.setImageResource(R.drawable.char_icon);
        selectedImageURI =null;
        m_et_name.getEditText().setText("");
        m_et_age.getEditText().setText("");
        m_et_sex.getEditText().setText("");
        m_et_race.getEditText().setText("");
        m_et_str.getEditText().setText("");
        m_et_dex.getEditText().setText("");
        m_et_con.getEditText().setText("");
        m_et_intl.getEditText().setText("");
        m_et_wis.getEditText().setText("");
        m_et_charm.getEditText().setText("");
    }

}