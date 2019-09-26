package com.ems.bdsqlite;

// Reproduzir este exemplo: https://www.androidpro.com.br/blog/armazenamento-de-dados/sqlite/

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Variáveis para os objetos da View
    EditText ra, nome, curso, campus;
    ListView listViewAlunos;
    TextView resultado;
    Button btInserir;

    // Cria um ArrayList para receber os dados dos objetos
    ArrayList<Aluno> alunos = new ArrayList<>();

    // Cria um ArrayAdapter para anexar à ListView
    ArrayAdapter<Aluno> adapter;

    // Cria uma variável para trabalhar com o SQLite
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associando os objetos da View
        ra = findViewById(R.id.editRa);
        nome = findViewById(R.id.editNome);
        curso = findViewById(R.id.editCurso);
        campus = findViewById(R.id.editCampus);
        btInserir = findViewById(R.id.btInserir);
        listViewAlunos = findViewById(R.id.listagem);
        resultado = findViewById(R.id.resultado);
/*
        // Cria alunos e insere no ArrayList
        alunos.add(new Aluno("123", "Edson Melo de Souza", "TADS", "MEMORIAL"));
        alunos.add(new Aluno("798", "Gustavo Pessarelo", "TGTI", "VERGUEIRO"));
        alunos.add(new Aluno("662", "Andrea Smulkowski", "Sistemas de Informação", "VILA MARIA"));
        alunos.add(new Aluno("938", "Elisa Souza", "Biologia", "SANTO AMARO"));
*/
        // Abre ou cria o banco de dados
        db = openOrCreateDatabase("db_aluno", Context.MODE_PRIVATE, null);

        // Cria a tabela se não existir, senão carrega a tabela para uso
        db.execSQL("CREATE TABLE IF NOT EXISTS aluno(ra VARCHAR, nome VARCHAR, curso VARCHAR, campus VARCHAR);");

        // Carrega os dados da tabela
        loadData();

        // Cria o Adapter para popular a ListView
        adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                alunos);

        // Anexa o adapter à ListView
        listViewAlunos.setAdapter(adapter);

        // Configura o clique para os itens da lista
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(position);
                resultado.setText(aluno.getDados());
            }
        });

        // Configura o botão para realizar a inserção no banco de dados
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Implementar lógica para evitar valores em branco ou nulos
                 */

                // Força a primeira letra em miúscula
                StringBuilder sb = new StringBuilder(nome.getText().toString());
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

                // Coleta os dados digitados nos campos
                ContentValues values = new ContentValues();
                values.put("ra", ra.getText().toString());
                values.put("nome", sb.toString());
                values.put("curso", curso.getText().toString());
                values.put("campus", campus.getText().toString());

                // Inserindo a linha na tabela
                db.insert("aluno", null, values);

                // Limpa os campos
                clearText();
                showMessage("Successo", "Aluno Incluído!");
                loadData();
                adapter.notifyDataSetChanged();

            }
        });
    }

    public void loadData() {
        // Carrega os dados existentes no banco de dados
        alunos.clear();
        Cursor c = db.rawQuery("SELECT * FROM aluno ORDER BY nome ASC", null);
        while (c.moveToNext()) {
            alunos.add(new Aluno(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
        }
    }

    /**
     * Caixa de Mensagem
     *
     * @param title   título da caixa de mensagem
     * @param message mensagem que será mostrada na tela
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Fechar", null);
        //builder.setIcon(R.drawable.dizzi);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * Limpa os campos de entrada
     */
    public void clearText() {
        ra.setText("");
        nome.setText("");
        curso.setText("");
        campus.setText("");
        ra.requestFocus();

        // fecha o teclado virtual
        ((InputMethodManager) MainActivity.this.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);
    }
}
