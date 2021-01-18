package com.caio.bliss.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.caio.bliss.R
import com.caio.bliss.application.MyApplication.Companion.emojiDatabase
import com.caio.bliss.application.MyApplication.Companion.scope
import com.caio.bliss.application.MyApplication.Companion.userDatabase
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.model.Repo
import com.caio.bliss.data.model.User
import com.caio.bliss.data.room.EmojiDAO
import com.caio.bliss.data.room.UserDAO
import com.caio.bliss.databinding.ActivityMainBinding
import com.caio.bliss.ui.recyclerview.ItemClickListener
import com.caio.bliss.ui.viewModel.MainActivityViewModel
import com.caio.bliss.util.loadImageWithUrl
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), ItemClickListener {

    private val viewModel by scope?.inject<MainActivityViewModel> {
        parametersOf(this)
    }

    private lateinit var emojiDAO: EmojiDAO
    private lateinit var userDAO: UserDAO
    private lateinit var emojiList: List<Emoji>
    private lateinit var userList: List<User>
    private lateinit var binding: ActivityMainBinding
    var int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        emojiDatabase?.emojiDAO()?.let { emojiDAO = it }
        userDatabase?.userDAO()?.let { userDAO = it }

        setupObservers()
        fetchEmojiList()
        setUserNameTextWatcher()
    }

    private fun goEmojiList() {
        startActivity(ListEmojiActivity.newIntent(this, emojiList))
    }

    override fun onResume() {
        super.onResume()
        usernameEditText.setText("")
    }

    private fun setupObservers() {
        viewModel.run {
            emojiResponse().observe(this@MainActivity, Observer {
                it?.let {
                    this@MainActivity.emojiList = it
                    for (i in this@MainActivity.emojiList) {
                        emojiDatabase?.emojiDAO()?.add(i)
                    }
                }
            })
            randomEmoji().observe(this@MainActivity, Observer {
                it?.let {
                    imageView.loadImageWithUrl(it.url)
                }
            })
            goList().observe(this@MainActivity, Observer {
                it?.let {
                    if (it) {
                        goEmojiList()
                    }
                }
            })
            userResponse().observe(this@MainActivity, Observer {
                it?.let {
                    userDatabase?.userDAO()?.add(it)
                    fetchUserListAndOpenAvatarScreen()
                }
            })
            statusLabel().observe(this@MainActivity, Observer {
                it?.let {
                    usernameEditText.error = getString(it)
                }
            })
            repoResponse().observe(this@MainActivity, Observer {
                openReposList(it)
            })
            error().observe(this@MainActivity, Observer {
                it?.let {
                    if (it) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.main_load_content_error), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    private fun setUserNameTextWatcher() {
        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.userName.value = s.toString()
            }
        })
    }

    private fun fetchEmojiList() {
        emojiDAO.all().run {
            if (this.isNullOrEmpty()) {
                viewModel.getEmojis()
            } else {
                emojiList = this
                viewModel.emojiList.value = this
            }
        }
    }

    private fun fetchUserListAndOpenAvatarScreen() {
        val list = ArrayList<Emoji>()
        userDAO.all().run {
            userList = userDAO.all()
        }
        for (i in userList) {
            list.add(Emoji(i.id.toString(), i.avatar_url))
        }
        startActivity(ListEmojiActivity.newIntent(this, list, this))
    }

    private fun openReposList(repos: List<Repo>) {
        int += 1
        startActivity(ListReposActivity.newIntent(this, repos))
    }

    override fun click(id: String) {
        userDAO.delete(id.toLong())
    }
}