package ru.mora.character_v2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListCharacterFragment extends Fragment
        implements View.OnClickListener{

    private static final String ARG_CHAR = "characters";
    ListView listView;
    ArrayList<Character> characters;


    // интервейс для реализации в активности
    public interface OnClickAddButtonListener {
        void onButtonClickAdd(int index);
    }

    // создание фрагмента
    public ListCharacterFragment() {
        // Required empty public constructor
    }

    // создание фрагмента с входными данными
    public static ListCharacterFragment newInstance(ArrayList<Character> characters) {
        ListCharacterFragment fragment = new ListCharacterFragment();
        // сохрание входных данных
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CHAR, characters);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // получение входных данных
        characters = getArguments().getParcelableArrayList(ARG_CHAR);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_character, container, false);

        listView = rootView.findViewById(R.id.list_char);

        // установка адаптера в список
        setCharacterList(characters);

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.fab);
        button.setOnClickListener(this);

        // клик на элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id){
                Toast.makeText(getActivity(), "position "+position + " id "+ id,
                        Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

    // клик на кнопку
    @Override
    public void onClick(View view) {
        // получение методов интерфейса реализованных в активности
        OnClickAddButtonListener listener = (OnClickAddButtonListener) getActivity();
        // запуск метода активности
        listener.onButtonClickAdd(R.id.fab);
    }

    public void setCharacterList(ArrayList<Character> characters) {
        // создаем адаптер заполняющий список
        CharacterAdapter characterAdapter = new CharacterAdapter(getContext(), characters);
        // устанавливаем адаптер
        listView.setAdapter(characterAdapter);
    }

    // адаптер для списка
    class CharacterAdapter extends BaseAdapter {
        LayoutInflater lInflater;
        ArrayList<Character> objects;

        CharacterAdapter(Context context, ArrayList<Character> character) {
            objects = character;
            lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // кол-во элементов
        @Override
        public int getCount() {
            return objects.size();
        }

        // элемент по позиции
        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        // id по позиции
        @Override
        public long getItemId(int position) {
            return position;
        }

        // пункт списка
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // используем созданные, но не используемые view
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.list_row, parent, false);
            }

            Character character = (Character) getItem(position);

            // заполняем View в пункте списка данными
            ((TextView) view.findViewById(R.id.list_tv_name)).setText(character.name);
            ((TextView) view.findViewById(R.id.list_tv_race)).setText(character.race);
            if (character.img_uri.equals("")){
                ((ImageView) view.findViewById(R.id.list_img_icon)).setImageResource(R.drawable.char_icon);
            }
            else {
                Uri uri = Uri.parse(character.img_uri);
                ((ImageView) view.findViewById(R.id.list_img_icon)).setImageURI(uri);
            }
            return view;
        }



    }
}